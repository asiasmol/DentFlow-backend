package com.dentflow.clinic.controller;
import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.clinic.model.ClinicResponse;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.exception.ApiRequestException;
import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.DoctorResponse;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRequest;
import com.dentflow.visit.model.VisitResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;



    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/myClinics")
    public Set<Clinic> getClinicByUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return clinicService.getAllUserClinicWhereWork(user.getEmail());
    }
    @GetMapping
    public Set<ClinicResponse> getAllClinic() {
        return clinicService.getAllClinic();
    }
    @PostMapping
    public void registerClinic(
            @RequestBody  ClinicRequest clinicRequest) {
        clinicService.registerClinic(clinicRequest);
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

    @DeleteMapping("/personnel")
    public void deleteEmployee(@RequestBody UserRequest userRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        clinicService.deleteEmployee(user.getEmail(), userRequest);
    }

    @GetMapping("/patients")
    public Set<Patient> getPatients(Authentication authentication, ClinicRequest clinicRequest){
        User user = (User) authentication.getPrincipal();
        Long clinicId = clinicRequest.getClinicId();
        if(!clinicService.checkIfClinicExists(clinicId)){
            throw new ApiRequestException("Cannot find clinic with that id" + clinicId);
        }
        return clinicService.getPatients(user.getEmail(), clinicId);
    }

    @GetMapping("/myDoctors")
    public Set<User> getDoctorsfromMyClinic(Authentication authentication, ClinicRequest clinicRequest){
        User user = (User) authentication.getPrincipal();
        Long clinicId = clinicRequest.getClinicId();

        if(!clinicService.checkIfClinicExists(clinicId)){
            throw new ApiRequestException("Cannot find clinic with that id" + clinicId);
        }

        return clinicService.getDoctorsfromMyClinic(user.getEmail(), clinicId);
    }
    @GetMapping("/doctors")
    public Set<DoctorResponse> getDoctorsfromClinic(ClinicRequest clinicRequest){
        Long clinicId = clinicRequest.getClinicId();

        if(!clinicService.checkIfClinicExists(clinicId)){
            throw new ApiRequestException("Cannot find clinic with that id" + clinicId);
        }

        return clinicService.getDoctorsByClinicId( clinicId);
    }
    @GetMapping("/visits")
    public Set<VisitResponse> getVisitsfromClinic(ClinicRequest clinicRequest){
        Long clinicId = clinicRequest.getClinicId();

        if(!clinicService.checkIfClinicExists(clinicId)){
            throw new ApiRequestException("Cannot find clinic with that id" + clinicId);
        }

        return clinicService.getVisitsByClinicId( clinicId);
    }


}
