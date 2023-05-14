package com.dentflow.hoursOfAvailability.model;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HoursOfAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String day;
    @Column(name = "fromHours")
    private String from;
    @Column(name = "toHours")
    private String to;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    @JsonIgnore
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
