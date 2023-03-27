package com.dentflow.patient.controller;

import com.dentflow.patient.model.Patient;
import com.dentflow.patient.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register_patient")
    public void registerPatientToClinic(@RequestBody Patient patient) {
        patientService.registerPatient(patient);
    }
    @GetMapping("/{patientId}")
    public Patient getPatientFromClinic(
            @PathVariable long patientId){
        return patientService.getPatient(patientId);
    }
}
