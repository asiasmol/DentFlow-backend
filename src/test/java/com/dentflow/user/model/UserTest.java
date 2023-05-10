package com.dentflow.user.model;

import static org.junit.jupiter.api.Assertions.*;

import com.dentflow.clinic.model.Clinic;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Clinic clinic1;
    private Clinic clinic2;

    @BeforeEach
    void setUp() {
        clinic1 = new Clinic();
        clinic2 = new Clinic();

        Set<Clinic> clinics = new HashSet<>();
        clinics.add(clinic1);
        clinics.add(clinic2);

        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .clinics(clinics)
                .roles(new HashSet<>())
                .build();
    }

    @Test
    void clearClinics() {
        assertEquals(2, user.getClinics().size());

        user.clearClinics();

        assertTrue(user.getClinics().isEmpty());
        assertFalse(clinic1.getPersonnel().contains(user));
        assertFalse(clinic2.getPersonnel().contains(user));
    }

    @Test
    void addRole() {
        assertTrue(user.getRoles().isEmpty());

        user.addRole(Role.USER);

        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(Role.USER));

        user.addRole(Role.OWNER);

        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(Role.OWNER));
    }
}