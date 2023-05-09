package com.dentflow.tooth.model;

import com.dentflow.description.model.Description;
import com.dentflow.patient.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "teeth")
public class Tooth {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toothId;

    private int number;

    @OneToMany
    private List<Description> descriptions;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Boolean caries;
    private Boolean noTooth;
    private Boolean cavity;
    private Boolean filling;
    private Boolean rootCanal;


}


