package com.dentflow.auth.controller;

import com.dentflow.auth.model.AuthenticationResponses;
import com.dentflow.auth.model.AuthenticationRequest;
import com.dentflow.auth.model.RegisterRequest;
import com.dentflow.auth.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import com.dentflow.user.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

//    @Test
//    public void testRegister() throws Exception {
//        RegisterRequest request = new RegisterRequest();
//        request.setFirstName("John");
//        request.setLastName("Doe");
//        request.setEmail("johndoe@example.com");
//        request.setPassword("password");
//
//        AuthenticationResponses response = AuthenticationResponses.builder()
//                .email(request.getEmail())
//                .roles(Collections.singleton(Role.USER))
//                .build();
//
//        when(authenticationService.register(request)).thenReturn(response);
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .body(new ObjectMapper().writeValueAsString(request)))
//                        .andExpect(status().isOk());
////                        .content(asJsonString(request)))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(request.getEmail())))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0]", is("USER")));
//
//        verify(authenticationService).register(request);
//    }

//    @Test
//    void shouldReturnOkStatusWhenRegistering() throws Exception {
//        RegisterRequest request = new RegisterRequest("John", "Doe", "john.doe@example.com", "password");
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        objectMapper.writeValue(outputStream, request);
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(outputStream.toByteArray()))
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void testAuthenticate() throws Exception {
//        AuthenticationRequest request = new AuthenticationRequest();
//        request.setEmail("johndoe@example.com");
//        request.setPassword("password");
//
//        AuthenticationResponses response = AuthenticationResponses.builder()
//                .email(request.getEmail())
//                .roles(Collections.singleton(Role.USER))
//                .token("jwtToken")
//                .build();
//
//        when(authenticationService.authenticate(request)).thenReturn(response);
//
//        mockMvc.perform(post("/api/auth/authenticate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is(request.getEmail())))
//                .andExpect(jsonPath("$.roles[0]", is("USER")))
//                .andExpect(jsonPath("$.token", is("jwtToken")));
//
//        verify(authenticationService).authenticate(request);
//    }
//
////     metoda pomocnicza do konwertowania obiektów na JSON
//    private static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}