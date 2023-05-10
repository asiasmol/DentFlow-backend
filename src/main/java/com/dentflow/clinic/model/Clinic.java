package com.dentflow.clinic.model;

import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.User;
import com.dentflow.visit.model.Visit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "clinics")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String address;
    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "owner_id",unique = true)
    private User owner;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "clinic_personnel",
            joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
//    @JsonIgnore
//    private Set<User> personnel;
    @Builder.Default
    private Set<User> personnel = new HashSet<>();
    @OneToMany
    @JsonIgnore
    @Column(name = "clinic_visits")
    private Set<Visit> visits;
    @OneToMany
    @JsonIgnore
    private Set<Patient> patients;

    public void addEmployee(User user){
        personnel.add(user);
    }
    public void removeEmployee(User user){
        personnel.remove(user);
//        user.getClinics().remove(this);
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }
}
