package com.dentflow.auth.controller;

import com.dentflow.auth.model.AuthenticationRequest;
import com.dentflow.auth.model.AuthenticationResponses;
import com.dentflow.auth.model.RegisterRequest;
import com.dentflow.auth.service.AuthenticationService;
import com.dentflow.user.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "johndoe@example.com", "password");
        AuthenticationResponses response = AuthenticationResponses.builder()
                .email("johndoe@example.com")
                .token("jwtToken")
                .roles(Collections.singleton(Role.USER))
                .build();

        when(authenticationService.register(registerRequest)).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.token").value("jwtToken"))
                .andExpect(jsonPath("$.roles[0]").value(Role.USER.toString()));
    }

    @Test
    void authenticate() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("johndoe@example.com", "password");
        AuthenticationResponses response = AuthenticationResponses.builder()
                .email("johndoe@example.com")
                .token("jwtToken")
                .roles(Collections.singleton(Role.USER))
                .build();

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(response);

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.token").value("jwtToken"))
                .andExpect(jsonPath("$.roles[0]").value(Role.USER.toString()));
    }
}