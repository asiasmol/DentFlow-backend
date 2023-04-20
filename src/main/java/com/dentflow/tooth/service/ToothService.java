package com.dentflow.tooth.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.tooth.model.ToothRepository;
import com.dentflow.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Service
public class ToothService {

    private final ToothRepository toothRepository;
    private final UserService userService;

    public ToothService(ToothRepository toothRepository, UserService userService) {
        this.toothRepository = toothRepository;
        this.userService = userService;
    }

    public Set<Tooth> getAllToothByPatientId(String email, Long clinicId, Long patientId){
        Clinic clinic = userService.getUser(email).getClinics().stream()
                .filter(c -> Objects.equals(c.getId(), clinicId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found: "));
        return clinic.getPatients().stream()
                .filter(patient -> patient.getPatientId()== patientId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found: "))
                .getTeeth();

    }
}

