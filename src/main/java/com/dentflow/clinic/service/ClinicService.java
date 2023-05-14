package com.dentflow.clinic.service;

import com.dentflow.auth.model.RegisterRequest;
import com.dentflow.auth.service.AuthenticationService;
import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.Role;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.model.UserRequest;
import com.dentflow.user.service.UserService;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.model.VisitRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class ClinicService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final UserService userService;
    private final AuthenticationService authService;

    public void registerClinic(ClinicRequest clinicRequest) {
        RegisterRequest ownerRegistrationRequest = new RegisterRequest(
                clinicRequest.getOwnerName(),
                clinicRequest.getOwnerLastname(),
                clinicRequest.getEmail(),
                clinicRequest.getPassword());

        authService.registerOwner(ownerRegistrationRequest);
        User user = userService.getUser(clinicRequest.getEmail());
        Clinic clinic = ClinicRequest.toEntity(clinicRequest,user);
        clinicRepository.save(clinic);
        user.setOwnedClinic(clinic);
        userRepository.save(user);
    }

    public Set<Clinic> getAllUserClinicWhereWork(String email) {
        return userService.getAllClinicsWhereWork(email);
    }

    public Clinic getClinicById(long clinicId) {
        return clinicRepository.findById(clinicId).get();

    }

    public void addEmployee(String myEmail, UserRequest userRequest) {
        Clinic clinic = userService.getUser(myEmail).getOwnedClinic();
        String workerEmail = userRequest.getEmail();
        User user= userService.getUser(workerEmail);
        user.addRole(userRequest.getRole());
        userRepository.save(user);
        clinic.addEmployee(user);
        clinicRepository.save(clinic);
    }

    public void deleteEmployee(String myEmail, UserRequest userRequest) {
        Clinic clinic = userService.getUser(myEmail).getOwnedClinic();
        String workerEmail = userRequest.getEmail();
        User user= userService.getUser(workerEmail);
        clinic.getPersonnel().remove(user);
        user.setRoles(new HashSet<>(Collections.singletonList(Role.USER)));
        userRepository.save(user);
        clinicRepository.save(clinic);
    }
    public Set<User> getPersonnel(String email) {
        return getMyClinic(email).getPersonnel();
    }

    public Set<Patient> getPatients(String email, long clinicId) {
        User user = userService.getUser(email);
        return user.getClinics().stream().filter(c -> c.getId() == clinicId).findFirst().orElse(user.getOwnedClinic()).getPatients();
    }


    public Clinic getMyClinic(String email) {
        return userService.getMyClinic(email);
    }

    public Set<User> getDoctors(String email, Long clinicId) {
        return userService.getUser(email).getClinics().stream().filter(clinic -> Objects.equals(clinic.getId(), clinicId))
                .findFirst().orElse(new Clinic())
                .getPersonnel().stream().filter(user -> user.getRoles().contains(Role.DOCTOR)).collect(Collectors.toSet());
    }
    public boolean checkIfClinicExists(Long clinicId) {
        return clinicRepository.existsById(clinicId);
    }
}
