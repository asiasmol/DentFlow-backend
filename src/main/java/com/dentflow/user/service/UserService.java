package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.model.UserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    public void addUser(User user) {
//        userRepository.save(user);
//    }

    public Set<String> getAllEmails(String email) {
        Set<String> users = userRepository.findAll().stream().filter(u -> u.getOwnedClinic() == null).map(User::getEmail).collect(Collectors.toSet());
        users.remove(email);
        Set<String> usersToRemove = getUser(email).getOwnedClinic().getPersonnel().stream().map(User::getEmail).collect(Collectors.toSet());
        users.removeAll(usersToRemove);
        return users;
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public void updateUser(String email, UserRequest request) {
        User user = getUser(email);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }

    public Set<Clinic> getAllClinicsWhereWork(String email) {
        return userRepository.findByEmail(email).get().getClinics();
    }

//    public void deleteUser(Long userId) {
//        User user = userRepository.findById(userId).get();
//        user.clearClinics();
//        userRepository.delete(user);
//    }
    public Clinic getMyClinic(String email) {
        return userRepository.findByEmail(email).get().getOwnedClinic();
    }

}
