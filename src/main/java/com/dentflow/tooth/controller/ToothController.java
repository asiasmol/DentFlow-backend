package com.dentflow.tooth.controller;

import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.tooth.service.ToothService;
import com.dentflow.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/teeth")
public class ToothController {

    private final ToothService toothService;

    public ToothController(ToothService toothService) {
        this.toothService = toothService;

    }

    @GetMapping
    public Set<Tooth> getAllTooth(@RequestBody PatientRequest patientRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return toothService.getAllToothByPatientId(user.getEmail(),  patientRequest.getClinicId(), patientRequest.getPatientId());
    }
}
