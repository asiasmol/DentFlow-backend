package com.dentflow.visit.controller;


import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.exception.ApiRequestException;
import com.dentflow.user.model.User;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.model.VisitRequest;
import com.dentflow.visit.model.VisitResponse;
import com.dentflow.visit.service.VisitService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/visits")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public Set<Visit> getVisitsByClinicId(ClinicRequest clinicRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return visitService.getVisitsByClinicId(user.getEmail(),clinicRequest.getClinicId());
    }
    @GetMapping("/doctor")
    public Set<Visit> getDocotrVisitsByClinicId(ClinicRequest clinicRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return visitService.getDoctorVisitsByClinicId(user.getEmail(),clinicRequest.getClinicId());
    }
    @PostMapping
    public void addVisitsByClinicId(@RequestBody VisitRequest visitRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        visitService.addVisitsToClinic(visitRequest, user.getEmail());
    }

    @PostMapping("/doctorDescription")
    public void addDoctorDescriptionToVisit(@RequestBody VisitRequest visitRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        visitService.saveDoctorDescriptionToVisit(visitRequest, user.getEmail());
    }
    @PostMapping("/ReceptionistDescription")
    public void addReceptionistDescriptionToVisit(@RequestBody VisitRequest visitRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        visitService.saveReceptionistDescriptionToVisitToVisit(visitRequest, user.getEmail());
    }
    @GetMapping("/myVisits")
    public Set<VisitResponse> getMyVisits(Authentication authentication) {
        if(authentication == null) {
            throw new ApiRequestException("Your token has expired");
        }
        User user = (User) authentication.getPrincipal();
        return visitService.getMyVisits(user.getEmail());
    }
    @PostMapping("/addVisitUser")
    public void addVisit(@RequestBody VisitRequest visitRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        visitService.addUserVisit(visitRequest, user.getEmail());
    }

}