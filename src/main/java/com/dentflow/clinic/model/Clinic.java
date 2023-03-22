package com.dentflow.clinic.model;

import com.dentflow.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "clinics")
@NoArgsConstructor
@Data
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;

    private String name;

    private String password;

    public Clinic(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "clinic_personnel",
            joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> personnel = new HashSet<>();


    public void addEmployee(User user, Clinic clinic){
        personnel.add(user);
    }
}
