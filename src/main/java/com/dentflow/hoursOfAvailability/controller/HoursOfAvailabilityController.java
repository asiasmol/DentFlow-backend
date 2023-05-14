package com.dentflow.hoursOfAvailability.controller;

import com.dentflow.hoursOfAvailability.model.HoursOfAvailability;
import com.dentflow.hoursOfAvailability.model.HoursOfAvailabilityRequest;
import com.dentflow.hoursOfAvailability.service.HoursOfAvailabilityService;
import com.dentflow.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/hours")
public class HoursOfAvailabilityController {

    private final HoursOfAvailabilityService hoursOfAvailabilityService;

    public HoursOfAvailabilityController(HoursOfAvailabilityService hoursOfAvailabilityService) {
        this.hoursOfAvailabilityService = hoursOfAvailabilityService;
    }
    @GetMapping
    public Set<HoursOfAvailability> getHoursOfAvailability(HoursOfAvailabilityRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return hoursOfAvailabilityService.getHoursOfAvailability(user.getEmail(),request);
    }
    @PatchMapping
    public void updateHoursOfAvailability(@RequestBody Set<HoursOfAvailabilityRequest> requestList, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        hoursOfAvailabilityService.updateHoursOfAvailability(user.getEmail(),requestList);
    }
}
