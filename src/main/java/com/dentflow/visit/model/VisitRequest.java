package com.dentflow.visit.model;

import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRequest;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitRequest {

    private int clinicId;

    private Date visitDate;
    private String visitTime;
    private int duration;
    private String doctorEmail;
    private int patientId;
    private String description;

    public static Visit toEntity(VisitRequest request) {
        return Visit.builder()
                .visitDate(request.getVisitDate())
                .visitTime(request.getVisitTime())
                .duration(request.getDuration())
                .description(request.getDescription())
                .build();
    }
}
