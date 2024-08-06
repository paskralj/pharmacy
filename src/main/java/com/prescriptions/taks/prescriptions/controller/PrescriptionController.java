package com.prescriptions.taks.prescriptions.controller;

import com.prescriptions.taks.prescriptions.dto.PrescriptionDTO;
import com.prescriptions.taks.prescriptions.entities.Prescription;
import com.prescriptions.taks.prescriptions.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing prescriptions.
 * Handles requests related to prescription creation and retrieval.
 */
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionController.class);

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * Creates a new prescription.
     *
     * @param requestedprescription The prescription details to be created.
     * @return A {@link ResponseEntity} containing the created {@link PrescriptionDTO} and a HTTP status of CREATED.
     */
    @Operation(summary = "Create a new prescription", description = "Creates a new prescription with the given details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prescription created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription requestedprescription) {
        logger.info("Request to create prescription for medicine name: {}", requestedprescription.getMedicineName());
        Prescription prescription = prescriptionService.createPrescription(requestedprescription);
        PrescriptionDTO prescriptionDTO = convertToDTO(prescription);
        logger.info("Created prescription with Id: {} and Name: {} ", prescriptionDTO.getDoctorId(),prescriptionDTO.getMedicineName());
        return ResponseEntity.status(HttpStatus.CREATED).body(prescriptionDTO);
    }

    /**
     * Retrieves a list of prescriptions for a specific doctor.
     *
     * @param doctorId The ID of the doctor whose prescriptions are to be retrieved.
     * @return A list of {@link PrescriptionDTO} objects associated with the specified doctor.
     */
    @Operation(summary = "Get prescriptions by doctor ID", description = "Retrieves a list of prescriptions for a specific doctor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/doctor/{doctorId}")
    public List<PrescriptionDTO> getPrescriptionsByDoctorId(@PathVariable Long doctorId) {
        logger.info("Request to get prescriptions for doctor ID: {}", doctorId);
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctorId(doctorId);
        logger.info("Found prescriptions! ");

        return prescriptions.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Converts a {@link Prescription} entity to a {@link PrescriptionDTO}.
     *
     * @param prescription The prescription entity to be converted.
     * @return The corresponding {@link PrescriptionDTO} object.
     */
    private PrescriptionDTO convertToDTO(Prescription prescription){
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(prescription.getId());
        dto.setMedicineName(prescription.getMedicineName());
        dto.setDosage(prescription.getDosage());
        dto.setInstructions(prescription.getInstructions());
        dto.setDoctorId(prescription.getDoctor().getId());
        dto.setPatientId(prescription.getPatient().getId());
        return dto;
    }

    /**
     * Retrieves a list of prescriptions for a specific patient.
     *
     * @param patientId The ID of the patient whose prescriptions are to be retrieved.
     * @return A list of {@link PrescriptionDTO} objects associated with the specified patient.
     */
    @Operation(summary = "Get prescriptions by patient ID", description = "Retrieves a list of prescriptions for a specific patient.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of prescriptions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/patient/{patientId}")
    public List<PrescriptionDTO> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        logger.info("Request to get prescriptions for patient ID: {}", patientId);
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatientId(patientId);
        logger.info("Found prescriptions. ");
        return prescriptions.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
