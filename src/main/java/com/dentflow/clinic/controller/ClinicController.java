package com.dentflow.clinic.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.service.PatientService;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRequest;
import com.dentflow.visit.service.VisitService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService,VisitService visitService,PatientService patientService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/myClinics")
    public Set<Clinic> getClinicByUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return clinicService.getAllUserClinicWhereWork(user.getEmail());
    }

    @PostMapping
    public void registerClinic(
            @RequestBody  ClinicRequest clinicRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        clinicService.registerClinic(clinicRequest,user);
    }

    @GetMapping("/myClinic")
    public Clinic get(Authentication authentication) {
        User user  = (User) authentication.getPrincipal();
        return clinicService.getMyClinic(user.getEmail());
    }

    @GetMapping("/personnel")
    public Set<User> getPersonnel(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return clinicService.getPersonnel(user.getEmail());
    }

    @PatchMapping("/personnel")
    public void addEmployee(@RequestBody UserRequest userRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        clinicService.addEmployee(user.getEmail(), userRequest);
    }

    @GetMapping("/patients")
    public Set<Patient> getPatients(Authentication authentication, ClinicRequest request){
        User user = (User) authentication.getPrincipal();
        return clinicService.getPatients(user.getEmail(), request.getClinicId());
    }

    @GetMapping("/doctors")
    public Set<User> getDoctors(Authentication authentication, ClinicRequest clinicRequest){
        User user = (User) authentication.getPrincipal();
        return clinicService.getDoctors(user.getEmail(), clinicRequest.getClinicId());
    }


//    @Transactional
//    @DeleteMapping("/{clinicId}")
//    public void deleteClinic(@PathVariable Long clinicId){
//        clinicService.deleteClinic(clinicId);
//    }
//
//    @Transactional
//    @DeleteMapping("/{clinicId}/personnel/{userId}")
//    public void removeUser(
//            @PathVariable("clinicId") Long clinicId,
//            @PathVariable("userId") Long userId){
//        clinicService.removeEmployee(userId, clinicId);
//    }

}
