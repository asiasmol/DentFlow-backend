package com.dentflow.clinic.model;

import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.User;
import com.dentflow.visit.model.Visit;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClinicTest {

    private Clinic clinic;
    private User owner;
    private User employee;
    private Visit visit;
    private Patient patient;

    @BeforeEach
    void setUp() {
        owner = new User();
        employee = new User();
        visit = new Visit();
        patient = new Patient();

        clinic = Clinic.builder()
                .id(1L)
                .name("Test Clinic")
                .owner(owner)
                .personnel(new HashSet<>())
                .visits(new HashSet<>())
                .patients(new HashSet<>())
                .build();
    }

    @Test
    void addEmployee() {
        clinic.addEmployee(employee);

        Set<User> personnel = clinic.getPersonnel();
        assertEquals(1, personnel.size());
        assertTrue(personnel.contains(employee));
    }

    @Test
    void removeEmployee() {
        clinic.getPersonnel().add(employee);
        clinic.removeEmployee(employee);

        Set<User> personnel = clinic.getPersonnel();
        assertEquals(0, personnel.size());
    }

    @Test
    void addVisit() {
        clinic.addVisit(visit);

        Set<Visit> visits = clinic.getVisits();
        assertEquals(1, visits.size());
        assertTrue(visits.contains(visit));
    }

    @Test
    void addPatient() {
        clinic.addPatient(patient);

        Set<Patient> patients = clinic.getPatients();
        assertEquals(1, patients.size());
        assertTrue(patients.contains(patient));
    }
}