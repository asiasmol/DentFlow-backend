package com.dentflow.patient.model;


import com.dentflow.clinic.model.Clinic;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.visit.model.Visit;
import com.dentflow.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User myUserAccount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic myClinic;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Email
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String pesel;
    private LocalDate birthDate;
    @JsonIgnore
    @OneToMany
    private Set<Visit> visits;
    @OneToMany
    private Set<Tooth> teeth;

    public void addTooth(Tooth tooth){
        teeth.add(tooth);
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }
}
