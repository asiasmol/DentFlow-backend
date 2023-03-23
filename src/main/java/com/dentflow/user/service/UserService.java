package com.dentflow.user.service;

import com.dentflow.clinic.controller.ClinicController;
import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public Optional<Set<Clinic>> getAllClinics(Long userId) {
        if (userRepository.findById(userId).isPresent()){
            return Optional.ofNullable(userRepository.findById(userId).get().getClinics());
        }
        return null;
    }


}
