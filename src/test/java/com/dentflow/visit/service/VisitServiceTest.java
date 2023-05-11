package com.dentflow.visit.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.service.PatientService;
import com.dentflow.user.model.User;
import com.dentflow.user.service.UserService;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.model.VisitRepository;
import com.dentflow.visit.model.VisitRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @InjectMocks
    private VisitService visitService;

    @Mock
    private VisitRepository visitRepository;
    @Mock
    private ClinicRepository clinicRepository;
    @Mock
    private UserService userService;
    @Mock
    private PatientService patientService;

    private User user;
    private Clinic clinic;
    private Visit visit;
    private VisitRequest visitRequest;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        user = new User();
        clinic = new Clinic();
        visit = new Visit();
        visitRequest = new VisitRequest();
        patient = new Patient();

        String clinicEmail = "test@example.com";
        Long clinidId = 1L;

//        visit.

        Set<Clinic> clinics = new HashSet<>();
        clinics.add(clinic);
        user.setClinics(clinics);
    }

    @Test
    public void testGetVisitsByClinicId() {
        when(userService.getUser(anyString())).thenReturn(user);
        Set<Visit> visits = visitService.getVisitsByClinicId("test@example.com", 1L);
        assertEquals(1, visits.size());
    }

    @Test
    public void testGetVisitsByClinicId_ClinicNotFound() {
        when(userService.getUser(anyString())).thenReturn(user);
        assertThrows(ResponseStatusException.class, () -> visitService.getVisitsByClinicId("test@example.com", 2L));
    }

    @Test
    public void testAddVisitsToClinic() {
        when(userService.getUser(anyString())).thenReturn(user);
        when(userService.getUser("doctor@example.com")).thenReturn(user);
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        when(clinicRepository.save(any(Clinic.class))).thenReturn(clinic);

        visitService.addVisitsToClinic(visitRequest, "test@example.com");

        verify(visitRepository, times(1)).save(any(Visit.class));
        verify(clinicRepository, times(1)).save(any(Clinic.class));
    }

    @Test
    public void testAddVisitsToClinic_ClinicNotFound() {
        when(userService.getUser(anyString())).thenReturn(user);
        visitRequest.setClinicId(2L);

        assertThrows(ResponseStatusException.class, () -> visitService.addVisitsToClinic(visitRequest, "test@example.com"));
    }
}
