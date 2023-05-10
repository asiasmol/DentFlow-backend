package com.dentflow.tooth.model;

import com.dentflow.description.model.Description;
import com.dentflow.patient.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToothRequest {
    private Long clinicId;
    private Long patientId;
    private ToothModel tooth;


}
