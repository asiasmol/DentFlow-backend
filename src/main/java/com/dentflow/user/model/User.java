package com.dentflow.user.model;

import com.dentflow.clinic.model.Clinic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
    @ManyToMany(mappedBy = "personnel")
    private Set<Clinic> clinics;
}
