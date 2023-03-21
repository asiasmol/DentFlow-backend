package com.dentflow.patient.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    private long clinicId;

    public Patient(String firstName, String lastName, String email, long clinicId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.clinicId = clinicId;
    }
}
