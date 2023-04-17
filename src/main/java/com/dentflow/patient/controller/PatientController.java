package com.dentflow.patient.controller;

import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.patient.service.PatientService;
import com.dentflow.user.model.User;
import org.springframework.security.core.Authentication;
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
    @PostMapping
    public void registerPatient(@RequestBody PatientRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out.println(request);
        patientService.registerPatient(request, user.getEmail());
    }
}
