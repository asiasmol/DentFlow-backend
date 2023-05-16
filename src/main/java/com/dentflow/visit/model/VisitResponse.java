package com.dentflow.visit.model;

import com.dentflow.hoursOfAvailability.model.HoursOfAvailability;
import com.dentflow.user.model.DoctorResponse;
import com.dentflow.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponse {
    private LocalDateTime visitDate;
    private DoctorResponse doctor;
    private int lengthOfTheVisit;
}
