package com.dentflow.hoursOfAvailability.model;

import com.dentflow.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface HoursOfAvailabilityRepository extends JpaRepository<HoursOfAvailability,Long> {
    Set<HoursOfAvailability> findByUserEmailAndClinicId (String userEmail, Long clinicId);
}
