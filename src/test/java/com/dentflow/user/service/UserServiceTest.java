package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.model.UserRequest;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setEmail("user1@example.com");

        user2 = new User();
        user2.setEmail("user2@example.com");
    }

    @Test
    void getAllEmails() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user1));

        Set<String> userEmails = userService.getAllEmails("user1@example.com");

        assertNotNull(userEmails);
        assertEquals(1, userEmails.size());
        assertEquals("user2@example.com", userEmails.iterator().next());
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user1));

        User foundUser = userService.getUser("user1@example.com");

        assertNotNull(foundUser);
        assertEquals("user1@example.com", foundUser.getEmail());
    }

    @Test
    void updateUser() {
        UserRequest request = new UserRequest();
        request.setFirstName("Updated");
        request.setLastName("User");
        request.setEmail("updated@example.com");

        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.updateUser("user1@example.com", request);

        assertEquals("Updated", user1.getFirstName());
        assertEquals("User", user1.getLastName());
        assertEquals("updated@example.com", user1.getEmail());
    }
}