package com.dentflow.user.model;

import com.dentflow.hoursOfAvailability.model.HoursOfAvailability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Set<HoursOfAvailability> hoursOfAvailability;
}
