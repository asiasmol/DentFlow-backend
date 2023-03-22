package com.dentflow.clinic.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinic")
@CrossOrigin
public class ClinicController {

    private ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/all")
    public List<Clinic> getClinic(){
        return clinicService.getAllClinic();
    }

    @GetMapping("/{clinicId}")
    public Clinic getClinicById(@PathVariable long clinicId) {
        return clinicService.getClinicById(clinicId);
    }

    @PostMapping("/register")
    public void registerClinic(
            @RequestBody Clinic clinic
    ){
        clinicService.registerClinic(clinic);
    }

    @GetMapping("/{clinicId}/personnel/all")
    public void getPersonnel(){
    }

    @GetMapping("/personnel/{userId}")
    public void getEmployee(){
    }

    @PostMapping("/{clinicId}/personnel/{userId}")
    public void addEmployee(){

    }

    @PostMapping("/{clinicId}/patient/{patientId}/add")
    public void addPatient(
        @PathVariable long clinicId,
        @PathVariable long patientId
    ){
        clinicService.addPatient(clinicId,patientId);
    }

    @GetMapping("/{clinicId}/patient/all")
    public List<Patient> getAllPatientsFromClinic(
            @PathVariable long clinicId) {
        return clinicService.getAllPatient(clinicId);
    }

    @DeleteMapping("/{clinicId}")
    public void deleteClinic(@PathVariable Long clinicId){
        clinicService.deleteClinic(clinicId);
    }

}
