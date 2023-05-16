package com.dentflow.visit.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.patient.service.PatientService;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.tooth.model.ToothRepository;
import com.dentflow.user.model.DoctorResponse;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.service.UserService;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.model.VisitRepository;
import com.dentflow.visit.model.VisitRequest;
import com.dentflow.visit.model.VisitResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final ClinicRepository clinicRepository;
    private final PatientRepository patientRepository;
    private final ToothRepository toothRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PatientService patientService;



    public VisitService(VisitRepository visitRepository, UserService userService, PatientService patientService, ClinicRepository clinicRepository, PatientRepository patientRepository, PatientRepository patientRepository1, ToothRepository toothRepository, UserRepository userRepository) {
        this.visitRepository = visitRepository;
        this.userService = userService;
        this.patientService = patientService;
        this.clinicRepository = clinicRepository;
        this.patientRepository = patientRepository1;
        this.toothRepository = toothRepository;
        this.userRepository = userRepository;
    }

    public Set<Visit> getVisitsByClinicId(String email, Long clinicId) {
        return userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), clinicId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found: ")).getVisits();
    }

    public void addVisitsToClinic(VisitRequest visitRequest, String email) {
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), visitRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        Visit visit = VisitRequest.toEntity(visitRequest);
        Patient patient = patientService.getPatientById(visitRequest.getPatientId());
        visit.setDoctor(userService.getUser(visitRequest.getDoctorEmail()));
        visit.setPatient(patient);
        visitRepository.save(visit);
        patient.addVisit(visit);
        patientRepository.save(patient);
        clinic.addVisit(visit);
        clinicRepository.save(clinic);
    }

    public void saveDoctorDescriptionToVisit(VisitRequest visitRequest, String email){
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), visitRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,"Clinic not found"));
        Visit visit = clinic.getVisits().stream().filter(v -> Objects.equals(v.getId(), visitRequest.getVisitId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Visit not found"));
        visit.setDoctorDescription(visitRequest.getDoctorDescription());
        visitRepository.save(visit);
    }

    public void saveReceptionistDescriptionToVisitToVisit(VisitRequest visitRequest, String email) {
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), visitRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,"Clinic not found"));
        Visit visit = clinic.getVisits().stream().filter(v -> Objects.equals(v.getId(), visitRequest.getVisitId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Visit not found"));
        visit.setReceptionistDescription(visitRequest.getReceptionistDescription());
        visitRepository.save(visit);
    }

    public Set<Visit> getDoctorVisitsByClinicId(String email, Long clinicId) {
        User user = userService.getUser(email);
        return user.getClinics().stream().filter(c -> Objects.equals(c.getId(), clinicId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found: ")).getVisits().stream().filter(visit -> Objects.equals(visit.getDoctor(), user)).collect(Collectors.toSet());
    }
    public Set<VisitResponse> getMyVisits(String email) {
        return userService.getUser(email).getMyPatientsAccounts().stream().flatMap(patient -> patient.getVisits()
                .stream().map(visit -> VisitResponse.builder().lengthOfTheVisit(visit.getLengthOfTheVisit()).visitDate(visit.getVisitDate()).clinicName(visit.getPatient().getMyClinic().getName())
                        .doctor(DoctorResponse
                                .builder()
                                .firstName(visit.getDoctor().getFirstName())
                                .lastName(visit.getDoctor().getLastName())
                                .email(visit.getDoctor().getEmail())
                                .hoursOfAvailability(visit.getDoctor().getHoursOfAvailability())
                                .build()).build()).collect(Collectors.toSet()).stream()).collect(Collectors.toSet());
    }

    public void addUserVisit(VisitRequest visitRequest, String email) {
        User user = userService.getUser(email);
        Clinic clinic = clinicRepository.findById(visitRequest.getClinicId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found: "));
        Visit visit = VisitRequest.toEntity(visitRequest);
        Patient patient;
        if(user.getMyPatientsAccounts().stream().anyMatch(p -> Objects.equals(p.getMyClinic().getId(), visitRequest.getClinicId()))){
            patient = user.getMyPatientsAccounts().stream().filter(p -> Objects.equals(p.getMyClinic().getId(), visitRequest.getClinicId())).findFirst().get();
        }else {
            patient =Patient.builder().firstName(user.getFirstName()).teeth(new HashSet<>()).visits(new HashSet<>()).lastName(user.getLastName()).build();
            patientRepository.save(patient);
            for(int i = 1; i <= 32; i++){
                patient.addTooth(toothRepository.save(Tooth.builder().number(i).forObservation(false).caries(false).secondaryCaries(false).filling(false).prostheticCrown(false).channelsFilledCorrectly(false).channelNotCompleted(false).periapicalChange(false).crownRootInsert(false).supragingivalCalculus(false).subgingivalCalculus(false).impactedTooth(false).noTooth(false).microdonticTooth(false).developmentalDefect(false).pathologicalClash(false)
                        .patient(patient).build()));
            }
            patient.setMyClinic(clinic);
            patient.setMyUserAccount(user);
            patientRepository.save(patient);
            clinic.addPatient(patient);
        }
        visit.setDoctor(userService.getUser(visitRequest.getDoctorEmail()));
        visit.setPatient(patient);
        visitRepository.save(visit);
        patient.addVisit(visit);
        patientRepository.save(patient);
        clinic.addVisit(visit);
        user.addMyPatientsAccount(patient);
        userRepository.save(user);
        clinicRepository.save(clinic);
    }
}
