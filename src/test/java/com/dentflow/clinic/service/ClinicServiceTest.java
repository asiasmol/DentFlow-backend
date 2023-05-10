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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClinicServiceTest {

    @InjectMocks
    private ClinicService clinicService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClinicRepository clinicRepository;

    @Mock
    private UserService userService;

    private User user;
    private ClinicRequest clinicRequest;
    private Clinic clinic;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        clinicRequest = new ClinicRequest();
        clinic = new Clinic();
        userRequest = new UserRequest();
    }

    @Test
    void registerClinic() {
        when(clinicRepository.save(any(Clinic.class))).thenReturn(clinic);
        when(userRepository.save(any(User.class))).thenReturn(user);

        clinicService.registerClinic(clinicRequest, user);

        verify(clinicRepository).save(any(Clinic.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getAllUserClinicWhereWork() {
        when(userService.getAllClinicsWhereWork(any(String.class))).thenReturn(Set.of(clinic));

        Set<Clinic> result = clinicService.getAllUserClinicWhereWork("test@example.com");

        assertEquals(1, result.size());
        assertEquals(clinic, result.iterator().next());
    }

    @Test
    void getClinicById() {
        when(clinicRepository.findById(any(Long.class))).thenReturn(Optional.of(clinic));

        Clinic result = clinicService.getClinicById(1L);

        assertEquals(clinic, result);
    }

    @Test
    void getClinicById_NotFound() {
        when(clinicRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> clinicService.getClinicById(1L));
    }

    @Test
    void addEmployee() {
        when(userService.getUser(any(String.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(clinicRepository.save(any(Clinic.class))).thenReturn(clinic);

        clinicService.addEmployee("test@example.com", userRequest);

        verify(userRepository).save(any(User.class));
        verify(clinicRepository).save(any(Clinic.class));
    }

    @Test
    void getPersonnel() {
        when(userService.getMyClinic(any(String.class))).thenReturn(clinic);

        Set<User> result = clinicService.getPersonnel("test@example.com");

        assertEquals(clinic.getPersonnel(), result);
    }

    @Test
    void getPatients() {
        user.setClinics(Set.of(clinic));
        when(userService.getUser(any(String.class))).thenReturn(user);

        Set<Patient> patients = clinic.getPatients();
        Set<Patient> result = clinicService.getPatients("test@example.com", clinic.getId());

        assertEquals(patients, result);
    }

    @Test
    void getMyClinic() {
        when(userService.getMyClinic(any(String.class))).thenReturn(clinic);
        Clinic result = clinicService.getMyClinic("test@example.com");

        assertEquals(clinic, result);
    }

    @Test
    void getDoctors() {
        user.setClinics(Set.of(clinic));
        User doctor = new User();
        doctor.addRole(Role.DOCTOR);
        clinic.setPersonnel(Set.of(doctor));
        when(userService.getUser(any(String.class))).thenReturn(user);

        Set<User> doctors = clinic.getPersonnel().stream().filter(user -> user.getRoles().contains(Role.DOCTOR)).collect(Collectors.toSet());
        Set<User> result = clinicService.getDoctors("test@example.com", clinic.getId());

        assertEquals(doctors, result);
    }

    @Test
    void getDoctors_ClinicNotFound() {
        user.setClinics(Set.of(clinic));
        when(userService.getUser(any(String.class))).thenReturn(user);

        assertThrows(ResponseStatusException.class, () -> clinicService.getDoctors("test@example.com", 999L));
    }
    }