package com.prescriptions.taks.prescriptions.repository;

import com.prescriptions.taks.prescriptions.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
