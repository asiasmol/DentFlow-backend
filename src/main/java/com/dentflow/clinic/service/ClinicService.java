package com.dentflow.clinic.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.model.UserRequest;
import com.dentflow.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Data
@AllArgsConstructor
public class ClinicService {

    private UserRepository userRepository;
    private ClinicRepository clinicRepository;
    private UserService userService;

    public void registerClinic(ClinicRequest clinicRequest, User user) {
        Clinic clinic = ClinicRequest.toEntity(clinicRequest,user);
        clinicRepository.save(clinic);
        user.setMyClinic(clinic);
        userRepository.save(user);
    }

    public Set<Clinic> getAllUserClinicWhereWork(String email) {
        return userService.getAllClinicsWhereWork(email);
    }

//    public List<Patient> getAllPatient(long clinicId) {
//        return null;
//    }
//
//    public void addPatient(long clinicId, long patientId) {
//    }
//    public Clinic getClinicById(long clinicId) {
//        if(clinicRepository.findById(clinicId).isPresent()){
//            return clinicRepository.findById(clinicId).get();
//        }
//        return null;
//    }

    public void addEmployee(String myEmail, UserRequest userRequest) {
        User user = userService.getUser(myEmail);
        String workerEmail = UserRequest.toEntity(userRequest).getEmail();
        user.getMyClinic().addEmployee(userService.getUser(workerEmail));
    }


    public Set<User> getPersonnel(Long clinicId) {
        Optional<Clinic> clinic = clinicRepository.findById(clinicId);
        if (clinic.isPresent()){
            return clinic.get().getPersonnel();
        } return null;
    }
//    public void deleteClinic(Long clinicId){
//        clinicRepository.delete(clinicRepository.findById(clinicId).get());
//    }
//
//    public void removeEmployee(Long userId, Long clinicId){
//        clinicRepository.findById(clinicId).get().removeEmployee(userService.getUser(userId));
//    }

    public Clinic getMyClinic(String email) {
        return userService.getMyClinic(email);
    }
}
