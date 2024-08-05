package com.prescriptions.taks.prescriptions.repository;

import com.prescriptions.taks.prescriptions.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
