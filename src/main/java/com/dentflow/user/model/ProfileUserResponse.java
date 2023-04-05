package com.dentflow.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private int passwordLength;
}
