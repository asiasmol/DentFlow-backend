package com.dentflow.user.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class UserResponseTest {

    @Test
    public void testUserResponse() {
        //given
        UserResponse userResponse = new UserResponse("John", "Doe", "john.doe@example.com");

        //then
        assertThat(userResponse.getFirstName()).isEqualTo("John");
        assertThat(userResponse.getLastName()).isEqualTo("Doe");
        assertThat(userResponse.getEmail()).isEqualTo("john.doe@example.com");
    }
}