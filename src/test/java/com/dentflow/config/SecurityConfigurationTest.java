package com.dentflow.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SecurityConfigurationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    // Testy konfiguracyjne
    @Test
    public void publicEndpointsAreAccessible() throws Exception {
        mockMvc.perform(get("/api/auth/login")).andExpect(status().isOk());
        mockMvc.perform(get("/api/users/profile")).andExpect(status().isOk());
        // Dodaj więcej ścieżek publicznych, jeśli są dostępne w Twojej aplikacji
    }

    @Test
    @WithMockUser
    public void securedEndpointsAreAccessibleWithMockUser() throws Exception {
        mockMvc.perform(get("/api/patients")).andExpect(status().isOk());
        mockMvc.perform(get("/api/clinics")).andExpect(status().isOk());
        // Dodaj więcej ścieżek zabezpieczonych, jeśli są dostępne w Twojej aplikacji
    }

    @Test
    public void securedEndpointsAreNotAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/patients")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/clinics")).andExpect(status().isUnauthorized());
        // Dodaj więcej ścieżek zabezpieczonych, jeśli są dostępne w Twojej aplikacji
    }
}