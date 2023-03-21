package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.model.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String firstName, String lastName, String email, UserType type, String password) {
        userRepository.save(new User(firstName, lastName, email, type, password));
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public Set<Clinic> getAllClinics(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getAllClinics).orElse(Collections.emptySet());
    }


}
