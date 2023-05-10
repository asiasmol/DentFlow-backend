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

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private final UserService userService;

    public UserServiceTest() {
        this.userService = new UserService(userRepository);
    }

//    @Test
//    void testGetUser() {
//        // given
//        String email = "test@example.com";
//        User user = new User();
//        user.setEmail(email);
//
//        // when
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        // then
//        User result = userService.getUser(email);
//        assertThat(result).isEqualTo(user);
//    }

    @Test
    void testGetUserNotFound() {
        // given
        String email = "test@example.com";

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userService.getUser(email))
                .withMessage("User not found");
    }

//    @Test
//    void testUpdateUser() {
//        // given
//        String email = "test@example.com";
//        UserRequest request = UserRequest.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email(email)
//                .build();
//        User user = new User();
//        user.setEmail(email);
//
//        // when
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//        userService.updateUser(email, request);
//
//        // then
//        assertThat(user.getFirstName()).isEqualTo(request.getFirstName());
//        assertThat(user.getLastName()).isEqualTo(request.getLastName());
//        assertThat(user.getEmail()).isEqualTo(request.getEmail());
//    }

//    @Test
//    void testGetAllClinicsWhereWork() {
//        // given
//        String email = "test@example.com";
//        User user = new User();
//        Clinic clinic1 = new Clinic();
//        Clinic clinic2 = new Clinic();
//        user.setEmail(email);
//        user.getClinics().add(clinic1);
//        user.getClinics().add(clinic2);
//
//        // when
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        // then
//        Set<Clinic> result = userService.getAllClinicsWhereWork(email);
//        assertThat(result).containsExactlyInAnyOrder(clinic1, clinic2);
//    }

//    @Test
//    void testGetAllEmails() {
//        // given
//        String email = "test@example.com";
//        User user = new User();
//        user.setEmail(email);
//        User user1 = new User();
//        user1.setEmail("test1@example.com");
//        user1.setOwnedClinic(new Clinic());
//        User user2 = new User();
//        user2.setEmail("test2@example.com");
//        user2.setClinics(new HashSet<>());
//        user2.getClinics().add(new Clinic());
//
//        // when
//        when(userRepository.findAll()).thenReturn(Set.of(user, user1, user2));
//        Set<String> result = userService.getAllEmails(email);
//
//        // then
//        assertThat(result).containsExactlyInAnyOrder(user1.getEmail(), user.getEmail());
//    }

//    @Test
//    public void testGetAllEmails() {
//        // given
//        User user = User.builder()
//                .id(1L)
//                .firstName("Jan")
//                .lastName("Kowalski")
//                .email("jan.kowalski@example.com")
//                .ownedClinic(null)
//                .build();
//        User user2 = User.builder()
//                .id(2L)
//                .firstName("Anna")
//                .lastName("Nowak")
//                .email("anna.nowak@example.com")
//                .ownedClinic(null)
//                .build();
//        User user3 = User.builder()
//                .id(3L)
//                .firstName("Adam")
//                .lastName("Malinowski")
//                .email("adam.malinowski@example.com")
//                .ownedClinic(null)
//                .build();
//        User user4 = User.builder()
//                .id(4L)
//                .firstName("Piotr")
//                .lastName("Kowalski")
//                .email("piotr.kowalski@example.com")
//                .ownedClinic(null)
//                .build();
//        User user5 = User.builder()
//                .id(5L)
//                .firstName("Magda")
//                .lastName("Nowak")
//                .email("magda.nowak@example.com")
//                .ownedClinic(null)
//                .build();
//        User user6 = User.builder()
//                .id(6L)
//                .firstName("Janina")
//                .lastName("Kowalska")
//                .email("janina.kowalska@example.com")
//                .ownedClinic(null)
//                .build();
//        Set<User> allUsers = Set.of(user, user2, user3, user4, user5, user6);
//        String email = "jan.kowalski@example.com";
//        user.setOwnedClinic(Clinic.builder().id(1L).name("Klinika Nowa").personnel(Set.of(user2, user3)).build());
//        user2.getClinics().add(user.getOwnedClinic());
//        user3.getClinics().add(user.getOwnedClinic());
//        user4.setOwnedClinic(Clinic.builder().id(2L).name("Klinika Stara").personnel(Set.of(user2, user5)).build());
//        user2.getClinics().add(user4.getOwnedClinic());
//        user5.getClinics().add(user4.getOwnedClinic());
//        Set<String> expected = Set.of("anna.nowak@example.com", "adam.malinowski@example.com");
//        when(userRepository.findAll()).thenReturn(allUsers);
//        // when
//        Set<String> actual = userService.getAllEmails(email);
//        // then
//        assertEquals(expected, actual);
//    }

    @Test
    void testGetUser() {
        // given
        User user = User.builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .ownedClinic(null)
                .build();
        String email = "jan.kowalski@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        // when
        User result = userService.getUser(email);
        // then
        assertEquals(user, result);
    }

//    @Test
//    void testGetUserNotFound() {
//        // arrange
//        String email = "notfound@test.com";
//        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        // act and assert
//        assertThrows(UsernameNotFoundException.class, () -> userService.getUser(email));
//
//        verify(userRepository, times(1)).findByEmail(email);
//    }
}