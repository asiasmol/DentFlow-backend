package com.dentflow.patient.controller;

import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.patient.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{patientId}")
    public Patient getPatientFromClinic(
            @PathVariable long patientId){
        return patientService.getPatient(patientId);
    }
}
