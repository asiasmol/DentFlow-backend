package com.dentflow.clinic.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.User;
import com.dentflow.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Data
@AllArgsConstructor
public class ClinicService {

    private ClinicRepository clinicRepository;
    private UserService userService;

    public void registerClinic(Clinic clinic) {
        clinicRepository.save(clinic);
    }

    public List<Clinic> getAllClinic() {
        return clinicRepository.findAll();
    }

    public List<Patient> getAllPatient(long clinicId) {
        return null;
    }

    public void addPatient(long clinicId, long patientId) {
    }
    public Clinic getClinicById(long clinicId) {
        if(clinicRepository.findById(clinicId).isPresent()){
            return clinicRepository.findById(clinicId).get();
        }
        return null;
    }

    public boolean addEmployee(Long clinicId, Long userId) {
        if (clinicRepository.findById(clinicId).isPresent()) {
            clinicRepository.findById(clinicId).get().addEmployee(userService.getUser(userId));
            return true;
        }
        return false;
    }

    public Set<User> getPersonnel(Long clinicId) {
        Optional<Clinic> clinic = clinicRepository.findById(clinicId);
        if (clinic.isPresent()){
            return clinic.get().getPersonnel();
        } return null;
    }
    public void deleteClinic(Long clinicId){
        clinicRepository.delete(clinicRepository.findById(clinicId).get());
    }

    public void removeEmployee(Long userId, Long clinicId){
        clinicRepository.findById(clinicId).get().removeEmployee(userService.getUser(userId));
    }

}
