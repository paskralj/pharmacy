package com.prescriptions.taks.prescriptions.controller;

import com.prescriptions.taks.prescriptions.entities.Doctor;
import com.prescriptions.taks.prescriptions.entities.Patient;
import com.prescriptions.taks.prescriptions.entities.User;
import com.prescriptions.taks.prescriptions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor){
        Doctor registeredDoctor = userService.registerDoctor(doctor);
        return ResponseEntity.ok(registeredDoctor);
    }

    @PostMapping("/register/patient")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient){
        Patient registeredPatient = userService.registerPatient(patient);
        return ResponseEntity.ok(registeredPatient);
    }
}
