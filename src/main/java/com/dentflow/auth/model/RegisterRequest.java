package com.dentflow.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String pesel;
    private String birthDate;
    private String email;
    private String password;

    public RegisterRequest(String ownerName, String ownerLastname, String email, String password) {
        this.firstName = ownerName;
        this.lastName = ownerLastname;
        this.email = email;
        this.password = password;

    }
}
