package com.dentflow.clinic.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ClinicService {

    private ClinicRepository clinicRepository;

    private PatientRepository patientRepository;

    public void registerClinic(Clinic clinic){
        clinicRepository.save(clinic);
    }

    public List<Clinic> getAllClinic(){
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
}
