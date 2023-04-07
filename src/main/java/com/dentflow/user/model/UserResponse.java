package com.dentflow.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;

}
