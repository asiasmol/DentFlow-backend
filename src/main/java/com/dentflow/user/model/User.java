package com.dentflow.user.model;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.patient.model.Patient;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String surName;
    private String email;
    private UserType userType;
    private String password;

    public User(String firstName, String surName, String email, UserType userType, String password) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.userType = userType;
        this.password = password;
    }
}
