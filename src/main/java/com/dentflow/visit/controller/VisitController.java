package com.dentflow.visit.controller;


import com.dentflow.user.model.User;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.service.VisitService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/visits")
public class VisitController {
    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }






}