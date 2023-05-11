package com.dentflow.user.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @InjectMocks
    UserRepository userRepository;

    @Mock
    private UserRepository mockRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEmail() {
        String testEmail = "test@test.com";
        User testUser = new User();
        testUser.setEmail(testEmail);

        when(mockRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userRepository.findByEmail(testEmail);

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
        verify(mockRepository, times(1)).findByEmail(testEmail);
    }

    @Test
    public void testFindByEmail_NotFound() {
        String testEmail = "test@test.com";

        when(mockRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByEmail(testEmail);

        assertFalse(foundUser.isPresent());
        verify(mockRepository, times(1)).findByEmail(testEmail);
    }
}