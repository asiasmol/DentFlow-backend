package com.dentflow.clinic.model;

import com.dentflow.user.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClinicRequestTest {

    @Test
    void toEntity() {
        User owner = new User();
//        ClinicRequest clinicRequest = ClinicRequest.builder()
//                .clinicId(1L)
//                .clinicName("Test Clinic")
//                .o(owner)
//                .build();
//
//        Clinic clinic = ClinicRequest.toEntity(clinicRequest, owner);
//
//        assertNotNull(clinic);
//        assertEquals(clinicRequest.getName(), clinic.getName());
//        assertEquals(clinicRequest.getOwner(), clinic.getOwner());
    }
}