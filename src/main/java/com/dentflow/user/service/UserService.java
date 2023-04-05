package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public Set<Clinic> getAllClinics(String email) {
        return userRepository.findByEmail(email).get().getClinics();

    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.clearClinics();
        userRepository.delete(user);
    }
}
