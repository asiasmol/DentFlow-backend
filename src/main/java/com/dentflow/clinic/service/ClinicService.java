package com.dentflow.clinic.service;

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

    public void registerClinic(ClinicRequest clinicRequest, User user) {
        Clinic clinic = ClinicRequest.toEntity(clinicRequest,user);
        clinicRepository.save(clinic);
        user.setOwnedClinic(clinic);
        user.addRole(Role.OWNER);
        userRepository.save(user);
    }

    public Set<Clinic> getAllUserClinicWhereWork(String email) {
        return userService.getAllClinicsWhereWork(email);
    }

//    public List<Patient> getAllPatient(long clinicId) {
//        return null;
//    }
//
//    public void addPatient(long clinicId, long patientId) {
//    }
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


    public Set<User> getPersonnel(String email) {
        return getMyClinic(email).getPersonnel();
    }

    public Set<Patient> getPatients(String email, long clinicId) {
        User user = userService.getUser(email);
        return user.getClinics().stream().filter(c -> c.getId() == clinicId).findFirst().orElse(user.getOwnedClinic()).getPatients();
    }

//    public void deleteClinic(Long clinicId){
//        clinicRepository.delete(clinicRepository.findById(clinicId).get());
//    }
//
//    public void removeEmployee(Long userId, Long clinicId){
//        clinicRepository.findById(clinicId).get().removeEmployee(userService.getUser(userId));
//    }

    public Clinic getMyClinic(String email) {
        return userService.getMyClinic(email);
    }

    public Set<User> getDoctors(String email, Long clinicId) {
        return userService.getUser(email).getClinics().stream().filter(clinic -> Objects.equals(clinic.getId(), clinicId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"))
                .getPersonnel().stream().filter(user -> user.getRoles().contains(Role.DOCTOR)).collect(Collectors.toSet());
    }
}
