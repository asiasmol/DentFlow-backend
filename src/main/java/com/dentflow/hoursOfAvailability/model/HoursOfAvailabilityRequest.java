package com.dentflow.hoursOfAvailability.model;

import com.dentflow.user.model.Role;
import com.dentflow.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoursOfAvailabilityRequest {
    private int clinicId;
    private String day;
    private String userEmail;
    private String from;
    private String to;
}
