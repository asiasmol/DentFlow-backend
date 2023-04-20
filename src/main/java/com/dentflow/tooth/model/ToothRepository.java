package com.dentflow.tooth.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToothRepository extends JpaRepository<Tooth, Long> {
}
