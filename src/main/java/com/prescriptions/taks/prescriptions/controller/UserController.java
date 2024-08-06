package com.prescriptions.taks.prescriptions.controller;

import com.prescriptions.taks.prescriptions.dto.DoctorDTO;
import com.prescriptions.taks.prescriptions.dto.PatientDTO;
import com.prescriptions.taks.prescriptions.entities.Doctor;
import com.prescriptions.taks.prescriptions.entities.Patient;
import com.prescriptions.taks.prescriptions.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user registrations.
 * Handles requests for registering doctors and patients.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Registers a new doctor.
     *
     * @param doctor The {@link Doctor} object containing details of the doctor to be registered.
     * @return A {@link ResponseEntity} containing the registered {@link DoctorDTO} and a HTTP status of CREATED.
     */
    @Operation(summary = "Register a new doctor", description = "Registers a new doctor with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/register/doctor")
    public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody Doctor doctor) {
        logger.info("Request to register doctor: {}", doctor.getUsername());
        Doctor registeredDoctor = userService.registerDoctor(doctor);
        DoctorDTO doctorDTO = convertDoctorToDTO(registeredDoctor);
        logger.info("Registered doctor: {}", doctorDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorDTO);
    }

    /**
     * Registers a new patient.
     *
     * @param patient The {@link Patient} object containing details of the patient to be registered.
     * @return A {@link ResponseEntity} containing the registered {@link PatientDTO} and a HTTP status of CREATED.
     */
    @Operation(summary = "Register a new patient", description = "Registers a new patient with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/register/patient")
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody Patient patient) {
        logger.info("Request to register patient: {}", patient.getUsername());
        Patient registeredPatient = userService.registerPatient(patient);
        PatientDTO patientDTO = convertPatientToDTO(registeredPatient);
        logger.info("Registered patient: {}", patientDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDTO);
    }

    private DoctorDTO convertDoctorToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setUsername(doctor.getUsername());
        dto.setSpecialty(doctor.getSpecialty());
        return dto;
    }

    private PatientDTO convertPatientToDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setUsername(patient.getUsername());
        dto.setSpecialty(patient.getMedicalHistory());
        return dto;
    }
}
