package com.dentflow.user.controller;

import com.dentflow.auth.AuthenticationResponses;
import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.GetUserResponse;
import com.dentflow.user.model.ProfileUserResponse;
import com.dentflow.user.model.User;
import com.dentflow.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/profile")
    public ProfileUserResponse getUserProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new ProfileUserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword().length());

    }

    @GetMapping("/{userId}/all_clinics")
    public Set<Clinic> getAllClinics(@PathVariable Long userId) {
        return userService.getUser(userId).getClinics();
    }

    @Transactional
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getUser")
    public ResponseEntity<GetUserResponse> getCurrentUser(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new GetUserResponse(user.getEmail()));
    }



}