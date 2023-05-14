package com.dentflow.patient.model;

import com.dentflow.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Set<Patient> findAllByEmail(String email);
}
