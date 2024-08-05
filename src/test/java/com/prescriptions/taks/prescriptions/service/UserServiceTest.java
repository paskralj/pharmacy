package com.prescriptions.taks.prescriptions.service;

import com.prescriptions.taks.prescriptions.entities.Doctor;
import com.prescriptions.taks.prescriptions.entities.Patient;
import com.prescriptions.taks.prescriptions.repository.DoctorRepository;
import com.prescriptions.taks.prescriptions.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit test class for {@link UserService}.
 * <p>
 * This class tests the functionality of the {@link UserService} methods
 * using Mockito for mocking dependencies and JUnit 5 for testing framework.
 * It contains tests for registering doctors and patients to ensure
 * the service methods interact correctly with the repository layer.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Tests the {@link UserService#registerDoctor(Doctor)} method.
     * <p>
     * This test verifies that the {@link UserService} correctly processes the registration
     * of a {@link Doctor} entity. It checks that the service method returns the expected
     * {@link Doctor} object, as provided by the mocked repository.
     * </p>
     */
    @Test
    void testRegisterDoctor() {
        Doctor doctor = new Doctor();
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor registeredDoctor = userService.registerDoctor(doctor);

        assertEquals(doctor, registeredDoctor);
    }

    /**
     * Tests the {@link UserService#registerPatient(Patient)} method.
     * <p>
     * This test verifies that the {@link UserService} correctly processes the registration
     * of a {@link Patient} entity. It checks that the service method returns the expected
     * {@link Patient} object, as provided by the mocked repository.
     * </p>
     */
    @Test
    void testRegisterPatient() {
        Patient patient = new Patient();
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient registeredPatient = userService.registerPatient(patient);

        assertEquals(patient, registeredPatient);
    }
}
