package com.dentflow.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    @Test
    void testBuilder() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        RegisterRequest request = RegisterRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();
        assertEquals(firstName, request.getFirstName());
        assertEquals(lastName, request.getLastName());
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }

    @Test
    void testConstructorAndGetters() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        RegisterRequest request = new RegisterRequest(firstName, lastName, email, password);
        assertEquals(firstName, request.getFirstName());
        assertEquals(lastName, request.getLastName());
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }
}