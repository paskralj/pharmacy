package com.prescriptions.taks.prescriptions.controller;

import com.prescriptions.taks.prescriptions.dto.PrescriptionDTO;
import com.prescriptions.taks.prescriptions.entities.Prescription;
import com.prescriptions.taks.prescriptions.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription requestedprescription) {
        Prescription prescription = prescriptionService.createPrescription(requestedprescription);
        PrescriptionDTO prescriptionDTO = convertToDTO(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(prescriptionDTO);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<PrescriptionDTO> getPrescriptionsByDoctorId(@PathVariable Long doctorId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctorId(doctorId);
        return prescriptions.stream()
                .map(this::convertToDTO)
                .toList();
    }

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

    @GetMapping("/patient/{patientId}")
    public List<PrescriptionDTO> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatientId(patientId);
        return prescriptions.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
