package com.prescriptions.taks.prescriptions.controller;

import com.prescriptions.taks.prescriptions.entities.Prescription;
import com.prescriptions.taks.prescriptions.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/create")
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Prescription> getPrescriptionsByDoctorId(@PathVariable Long doctorId) {
        return prescriptionService.getPrescriptionsByDoctorId(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        return prescriptionService.getPrescriptionsByPatientId(patientId);
    }
}
