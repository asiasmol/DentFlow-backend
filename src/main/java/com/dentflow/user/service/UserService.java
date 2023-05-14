package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.model.UserRequest;
import com.dentflow.visit.model.Visit;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;


    public UserService(UserRepository userRepository, PatientRepository patientRepository, ClinicRepository clinicRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.clinicRepository = clinicRepository;
    }


    public Set<String> getAllEmails(String email) {
        Set<String> users = userRepository.findAll().stream().filter(u -> u.getOwnedClinic() == null).map(User::getEmail).collect(Collectors.toSet());
        users.remove(email);
        Clinic ownedClinic = getUser(email).getOwnedClinic();
        if (ownedClinic != null && ownedClinic.getPersonnel() != null) {
            Set<String> usersToRemove = ownedClinic.getPersonnel().stream().map(User::getEmail).collect(Collectors.toSet());
            users.removeAll(usersToRemove);
        }
        return users;
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public void updateUser(String email, UserRequest request) {
        User user = getUser(email);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);
    }

    public Set<Clinic> getAllClinicsWhereWork(String email) {
        return userRepository.findByEmail(email).get().getClinics();
    }
    public Clinic getMyClinic(String email) {
        return userRepository.findByEmail(email).get().getOwnedClinic();
    }


    public Set<Clinic> getAllMyPatientAccountsClinic(String email) {
        User user = getUser(email);
        Set<Patient> patients = patientRepository.findAllByEmail(email);
        Set<Patient> filterPatients = patients.stream()
                .filter(item -> !user.getMyPatientsAccounts().contains(item))
                .collect(Collectors.toSet());
        return new HashSet<>(filterPatients).stream().map(Patient::getMyClinic)
                .collect(Collectors.toSet());
    }

    public void addPatientAccount(String email, ClinicRequest request) {
        User user  = getUser(email);
        Patient myPatientAccount = clinicRepository.findById(request.getClinicId())
                .get().getPatients()
                .stream().filter(patient -> Objects.equals(patient.getEmail(), user.getEmail())).findFirst().get();
        myPatientAccount.setMyUserAccount(user);
        patientRepository.save(myPatientAccount);
        user.addMyPatientsAccount(myPatientAccount);
        userRepository.save(user);
    }
}
