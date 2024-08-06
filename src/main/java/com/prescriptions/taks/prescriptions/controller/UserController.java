package com.prescriptions.taks.prescriptions.controller;

import com.prescriptions.taks.prescriptions.dto.DoctorDTO;
import com.prescriptions.taks.prescriptions.dto.PatientDTO;
import com.prescriptions.taks.prescriptions.entities.Doctor;
import com.prescriptions.taks.prescriptions.entities.Patient;
import com.prescriptions.taks.prescriptions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/doctor")
    public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody Doctor doctor) {
        Doctor registeredDoctor = userService.registerDoctor(doctor);
        DoctorDTO doctorDTO = convertDoctorToDTO(registeredDoctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorDTO);
    }

    @PostMapping("/register/patient")
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody Patient patient) {
        Patient registeredPatient = userService.registerPatient(patient);
        PatientDTO patientDTO = convertPatientToDTO(registeredPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDTO);
    }

    private DoctorDTO convertDoctorToDTO(Doctor doctor){
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setUsername(doctor.getUsername());
        dto.setSpecialty(doctor.getSpecialty());
        return dto;
    }

    private PatientDTO convertPatientToDTO(Patient patient){
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setUsername(patient.getUsername());
        dto.setSpecialty(patient.getMedicalHistory());
        return dto;
    }
}
