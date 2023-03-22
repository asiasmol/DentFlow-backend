package com.dentflow.clinic.model;

import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    public Clinic(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @ManyToMany
    @JoinTable
    private Set<User> personnel;

    public void addEmployee(User user){
        personnel.add(user);
    }
}
