package com.dentflow.hoursOfAvailability.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.hoursOfAvailability.model.HoursOfAvailability;
import com.dentflow.hoursOfAvailability.model.HoursOfAvailabilityRepository;
import com.dentflow.hoursOfAvailability.model.HoursOfAvailabilityRequest;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HoursOfAvailabilityService {
    private HoursOfAvailabilityRepository hoursOfAvailabilityRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public HoursOfAvailabilityService(UserService userService, ClinicService clinicService, HoursOfAvailabilityRepository hoursOfAvailabilityRepository, UserRepository userRepository) {
        this.userService = userService;
        this.hoursOfAvailabilityRepository = hoursOfAvailabilityRepository;
        this.userRepository = userRepository;
    }

    public void updateHoursOfAvailability(String email, Set<HoursOfAvailabilityRequest> requestList) {
        User user = userService.getUser(email);
        Set<HoursOfAvailability> hoursOfAvailabilitys = new HashSet<>();
        boolean canDelete=true;

        for (HoursOfAvailabilityRequest request : requestList) {
            Clinic clinic = user.getClinics().stream()
                    .filter(c -> c.getId() == request.getClinicId())
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Clinic not found"));
           if (canDelete){
               Set<HoursOfAvailability> existingHoursOfAvailability = hoursOfAvailabilityRepository.findByUserEmailAndClinicId(user.getEmail(), clinic.getId());
               hoursOfAvailabilityRepository.deleteAll(existingHoursOfAvailability);
               canDelete=false;
           }

            HoursOfAvailability hoursOfAvailability = HoursOfAvailability.builder()
                    .clinic(clinic)
                    .user(user)
                    .day(request.getDay())
                    .from(request.getFrom())
                    .to(request.getTo())
                    .build();

            hoursOfAvailabilityRepository.save(hoursOfAvailability);
            hoursOfAvailabilitys.add(hoursOfAvailability);
        }

        user.setHoursOfAvailability(hoursOfAvailabilitys);
        userRepository.save(user);
    }


    public Set<HoursOfAvailability> getHoursOfAvailability(String email, HoursOfAvailabilityRequest request) {
        User user = userService.getUser(email);
        return user.getHoursOfAvailability().stream().filter(hoursOfAvailability -> hoursOfAvailability.getClinic().getId() == request.getClinicId()).collect(Collectors.toSet());

    }
}
