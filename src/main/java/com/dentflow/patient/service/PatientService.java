package com.dentflow.patient.service;

import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void registerPatient(String firstName, String lastName, String email, long clinicId) {
        patientRepository.save(new Patient(firstName, lastName, email, clinicId));
    }

    public Patient getPatient(long patientId) {
        return patientRepository.findById(patientId).get();
    }

    public List<Patient> getAllPatientsFromClinic(long clinicId) {
        return patientRepository.findAllByClinicId(clinicId);
    }
}
