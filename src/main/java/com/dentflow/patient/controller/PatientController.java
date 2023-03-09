package com.dentflow.patient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/{clinicId}/patients")
public class PatientController {
    @PostMapping("/add")
    public void addPatientToClinic(){

    }
    @GetMapping("/{patientId}")
    public void getPatientFromClinic(){
    }
    @GetMapping("/all")
    public void getAllPatientsFromClinic(){

    }
}
