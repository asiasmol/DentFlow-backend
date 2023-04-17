package com.dentflow.visit.model;

import com.dentflow.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit,Long> {

}
