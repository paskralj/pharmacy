package com.prescriptions.taks.prescriptions.service;

import com.prescriptions.taks.prescriptions.entities.Doctor;
import com.prescriptions.taks.prescriptions.entities.Patient;
import com.prescriptions.taks.prescriptions.entities.Prescription;
import com.prescriptions.taks.prescriptions.repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Unit test class for {@link PrescriptionService}.
 * <p>
 * This class contains unit tests for the {@link PrescriptionService} class,
 * specifically testing its methods for creating and retrieving prescriptions.
 * The tests ensure that the service methods correctly interact with the repository
 * layer and return the expected results.
 * </p>
 * <p>
 * The tests use Mockito to mock the {@link PrescriptionRepository} dependency,
 * allowing for isolated testing of the {@link PrescriptionService} functionality.
 * JUnit 5 is used as the testing framework.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionRepository prescriptionRepository;
    private Prescription prescription1;
    private Doctor doctor;
    private Patient patient;

    /**
     * Initializes test data before each test method is executed.
     * Sets up a {@link Prescription}, {@link Doctor} and a {@link Patient} instance for use in the tests.
     */
    @BeforeEach
    public void setUp() {
        prescription1 = new Prescription();
        prescription1.setId(1L);

        doctor = new Doctor();
        doctor.setId(2L);

        patient = new Patient();
        patient.setId(3L);
    }

    /**
     * Tests the {@link PrescriptionService#createPrescription(Prescription)} method.
     * <p>
     * This test verifies that the {@link PrescriptionService} correctly processes the creation
     * of a {@link Prescription} entity. It checks that the service method returns the expected
     * {@link Prescription} object, as provided by the mocked repository.
     * </p>
     */
    @Test
    void testCreatePrescription() {
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(prescription1);
        Prescription result = prescriptionService.createPrescription(prescription1);

        assertNotNull(result);
        assertEquals(prescription1.getId(), result.getId());
        assertEquals(prescription1, prescriptionService.createPrescription(prescription1));
    }

    /**
     * Tests the {@link PrescriptionService#getPrescriptionsByDoctorId(Long)} method.
     * <p>
     * This test verifies that the {@link PrescriptionService} correctly retrieves prescriptions
     * associated with a given {@link Doctor} ID. It checks that the service method returns
     * the expected list of {@link Prescription} objects, as provided by the mocked repository.
     * </p>
     */
    @Test
    void testGetPrescriptionsByDoctorId() {
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription1);

        when(prescriptionRepository.findByDoctorId(doctor.getId())).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.getPrescriptionsByDoctorId(doctor.getId());

        assertEquals(prescriptions.size(), result.size());
    }

    /**
     * Tests the {@link PrescriptionService#getPrescriptionsByPatientId(Long)} method.
     * <p>
     * This test verifies that the {@link PrescriptionService} correctly retrieves prescriptions
     * associated with a given {@link Patient} ID. It checks that the service method returns
     * the expected list of {@link Prescription} objects, as provided by the mocked repository.
     * </p>
     */
    @Test
    void testGetPrescriptionsByPatientId() {
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription1);

        when(prescriptionRepository.findByPatientId(patient.getId())).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.getPrescriptionsByPatientId(patient.getId());

        assertEquals(prescriptions.size(), result.size());
    }

}
