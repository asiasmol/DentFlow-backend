package com.dentflow.user.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserRequestTest {
    @Test
    public void testToEntity() {
        // given
        UserRequest request = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role(Role.USER)
                .build();

        // when
        User user = UserRequest.toEntity(request);

        // then
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getEmail(), user.getEmail());
    }

    @Test
    public void testToEntityNullValues() {
        // given
        UserRequest request = UserRequest.builder().build();

        // when
        User user = UserRequest.toEntity(request);

        // then
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
    }
}