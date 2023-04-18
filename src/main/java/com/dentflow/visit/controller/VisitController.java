package com.dentflow.visit.controller;


import com.dentflow.user.model.User;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.model.VisitRequest;
import com.dentflow.visit.service.VisitService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/visits")
public class VisitController {
    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public Set<Visit> getVisitsByClinicId(@RequestBody VisitRequest visitRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return visitService.getVisitsByClinicId(user.getEmail(),visitRequest.getClinicId());
    }

    @PostMapping
    public void addVisitsByClinicId(@RequestBody VisitRequest visitRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out.println(visitRequest);
//        visitService.addVisitsToClinic(visitRequest, user.getEmail());
    }




}