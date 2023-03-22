package com.dentflow.user.model;

import com.dentflow.clinic.model.Clinic;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
    private String lastName;
    private String email;
    private UserType userType;
    private String password;

    public User(String firstName, String lastName, String email, UserType userType, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userType = userType;
        this.password = password;
    }
    @ManyToMany(mappedBy = "personnel")
    private Set<Clinic> clinics = new HashSet<>();
}
