package com.dentflow.user.model;

import com.dentflow.clinic.model.Clinic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private Set<Role> roles;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "personnel")
    private Set<Clinic> clinicsWhereWork;

    @OneToOne
    @JoinColumn(name = "clinic_id")
    private Clinic myClinic;

    public void clearClinics(){
        for (Clinic clinic : clinicsWhereWork) {
            clinic.removeEmployee(this);
        }
        this.clinicsWhereWork.clear();
    }

    public void addClinic(Clinic clinic){
        clinicsWhereWork.add(clinic);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
