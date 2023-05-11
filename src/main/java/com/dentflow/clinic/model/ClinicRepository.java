package com.dentflow.clinic.model;


import com.dentflow.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClinicRepository extends JpaRepository<Clinic,Long> {

    Clinic findByOwner(User user);

}
