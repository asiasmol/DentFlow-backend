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

public class UserTest {

    @Test
    void testClearClinics() {
        // given
        User user = new User();
        Clinic clinic1 = new Clinic();
        Clinic clinic2 = new Clinic();
        Set<Clinic> clinics = new HashSet<>(Arrays.asList(clinic1, clinic2));
        user.setClinics(clinics);

        // when
        user.clearClinics();

        // then
        assertTrue(user.getClinics().isEmpty());
        assertFalse(clinic1.getPersonnel().contains(user));
        assertFalse(clinic2.getPersonnel().contains(user));
    }

    @Test
    void testAddRole() {
        // given
        User user = new User();
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);

        // when
        user.addRole(Role.USER);

        // then
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(Role.USER));
    }

    @Test
    void testGetAuthorities() {
        // given
        User user = new User();
        Set<Role> roles = new HashSet<>(Arrays.asList(Role.USER, Role.OWNER));
        user.setRoles(roles);

        // when
        Set<SimpleGrantedAuthority> authorities = new HashSet<>((Collection) user.getAuthorities());

        // then
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("OWNER")));
    }

    @Test
    void testGetUsername() {
        // given
        User user = new User();
        user.setEmail("test@example.com");

        // when
        String username = user.getUsername();

        // then
        assertEquals("test@example.com", username);
    }

    @Test
    void testIsAccountNonExpired() {
        // given
        User user = new User();

        // when
        boolean accountNonExpired = user.isAccountNonExpired();

        // then
        assertTrue(accountNonExpired);
    }

    @Test
    void testIsAccountNonLocked() {
        // given
        User user = new User();

        // when
        boolean accountNonLocked = user.isAccountNonLocked();

        // then
        assertTrue(accountNonLocked);
    }

    @Test
    void testIsCredentialsNonExpired() {
        // given
        User user = new User();

        // when
        boolean credentialsNonExpired = user.isCredentialsNonExpired();

        // then
        assertTrue(credentialsNonExpired);
    }

    @Test
    void testIsEnabled() {
        // given
        User user = new User();

        // when
        boolean enabled = user.isEnabled();

        // then
        assertTrue(enabled);
    }
}