package com.dentflow.clinic.model;

import com.dentflow.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "clinics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
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
    @JsonIgnore
    private Set<User> personnel;

    public void addEmployee(User user){
        personnel.add(user);
    }
    public void removeEmployee(User user){
        personnel.remove(user);
//        user.getClinics().remove(this);
    }

}
