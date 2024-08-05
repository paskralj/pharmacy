package com.prescriptions.taks.prescriptions.service;

import com.prescriptions.taks.prescriptions.entities.Doctor;
import com.prescriptions.taks.prescriptions.entities.Patient;
import com.prescriptions.taks.prescriptions.entities.User;
import com.prescriptions.taks.prescriptions.repository.DoctorRepository;
import com.prescriptions.taks.prescriptions.repository.PatientRepository;
import com.prescriptions.taks.prescriptions.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Doctor registerDoctor(Doctor doctor) {
        doctor.setRole("DOCTOR");
        return doctorRepository.save(doctor);
    }

    public Patient registerPatient(Patient patient) {
        patient.setRole("PATIENT");
        return patientRepository.save(patient);
    }
}
