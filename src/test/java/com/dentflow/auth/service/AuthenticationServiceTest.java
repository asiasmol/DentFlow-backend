package com.dentflow.auth.service;

import com.dentflow.auth.model.AuthenticationRequest;
import com.dentflow.auth.model.AuthenticationResponses;
import com.dentflow.auth.model.RegisterRequest;
import com.dentflow.config.jwt.JwtService;
import com.dentflow.user.model.Role;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    public AuthenticationServiceTest() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void register() {
// given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password("encodedPassword")
                .roles(Collections.singleton(Role.USER))
                .build();

        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // when
        AuthenticationResponses response = authenticationService.register(registerRequest);

        // then
        assertEquals(registerRequest.getEmail(), response.getEmail());
        assertEquals(Collections.singleton(Role.USER), response.getRoles());
        assertEquals("jwtToken", response.getToken());

        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(userRepository).save(user);
        verify(jwtService).generateToken(user);
    }

    @Test
    void authenticate() {
        String email = "john.doe@example.com";
        String password = "password123";
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        User user = User.builder()
                .email(email)
                .password("encodedPassword")
                .roles(Collections.singleton(Role.USER))
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        AuthenticationResponses response = authenticationService.authenticate(request);

        assertEquals("jwtToken", response.getToken());
        assertEquals(email, response.getEmail());
        assertEquals(Collections.singleton(Role.USER), response.getRoles());
    }
}