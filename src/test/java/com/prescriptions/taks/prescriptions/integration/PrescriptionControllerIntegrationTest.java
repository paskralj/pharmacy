package com.prescriptions.taks.prescriptions.integration;

import com.prescriptions.taks.prescriptions.entities.Doctor;
import com.prescriptions.taks.prescriptions.entities.Patient;
import com.prescriptions.taks.prescriptions.entities.Prescription;
import com.prescriptions.taks.prescriptions.repository.DoctorRepository;
import com.prescriptions.taks.prescriptions.repository.PatientRepository;
import com.prescriptions.taks.prescriptions.repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test class for the {@link com.prescriptions.taks.prescriptions.controller.PrescriptionController} class.
 *
 * This test class is responsible for verifying the integration of the
 * PrescriptionController endpoints with the underlying services and repositories.
 * It uses MockMvc to perform HTTP requests and assert the responses.
 *
 * The class is annotated with:
 * - {@link SpringBootTest} to load the full application context for integration testing.
 * - {@link AutoConfigureMockMvc} to configure MockMvc, which is used to perform HTTP requests.
 * - {@link ActiveProfiles} to specify the active profile as "test" for using test-specific configurations.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PrescriptionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    private Doctor doctor;
    private Patient patient;
    private Prescription prescription;

    /**
     * Sets up the test data before each test.
     *
     * This method initializes the Doctor, Patient, and Prescription entities,
     * saves them to the respective repositories, and prepares the data for the
     * test methods.
     */
    @BeforeEach
    void setUp() {
        prescriptionRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();

        doctor = new Doctor();
        doctor.setUsername("dr.suba");
        doctor.setPassword("password");
        doctor.setSpecialty("Cardiology");

        patient = new Patient();
        patient.setUsername("PacijentJedan");
        patient.setPassword("password");
        patient.setMedicalHistory("Kako si ziv");

        doctorRepository.save(doctor);
        patientRepository.save(patient);

        prescription = new Prescription();
        prescription.setMedicineName("Aspirin");
        prescription.setDosage("356mg");
        prescription.setInstructions("Take once daily");
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);

        prescriptionRepository.save(prescription);
    }

    /**
     * Tests the endpoint for retrieving prescriptions by doctor ID.
     *
     * Performs a GET request to the /api/prescriptions/doctor/{id} endpoint,
     * where {id} is replaced by the doctor's ID. Asserts that the response status
     * is 200 OK and verifies the content of the response using JSON path expressions.
     *
     * @throws Exception if an error occurs while performing the request or processing the response
     */
    @Test
    void testGetPrescriptionsByDoctorId() throws Exception {

        mockMvc.perform(get("/api/prescriptions/doctor/" + doctor.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(prescription.getId()))
                .andExpect(jsonPath("$[0].medicineName").value(prescription.getMedicineName()))
                .andExpect(jsonPath("$[0].dosage").value(prescription.getDosage()))
                .andExpect(jsonPath("$[0].instructions").value(prescription.getInstructions()))
                .andExpect(jsonPath("$[0].doctorId").value(doctor.getId()))
                .andExpect(jsonPath("$[0].patientId").value(patient.getId()));
    }

    /**
     * Tests the endpoint for retrieving prescriptions by patient ID.
     *
     * Performs a GET request to the /api/prescriptions/patient/{id} endpoint,
     * where {id} is replaced by the patient's ID. Asserts that the response status
     * is 200 OK and verifies the content of the response using JSON path expressions.
     *
     * @throws Exception if an error occurs while performing the request or processing the response
     */
    @Test
    void testGetPrescriptionsByPatientId() throws Exception {

        mockMvc.perform(get("/api/prescriptions/patient/" + patient.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(prescription.getId()))
                .andExpect(jsonPath("$[0].medicineName").value(prescription.getMedicineName()))
                .andExpect(jsonPath("$[0].dosage").value(prescription.getDosage()))
                .andExpect(jsonPath("$[0].instructions").value(prescription.getInstructions()))
                .andExpect(jsonPath("$[0].doctorId").value(doctor.getId()))
                .andExpect(jsonPath("$[0].patientId").value(patient.getId()));

    }

    /**
     * Tests the endpoint for creating a new prescription.
     *
     * Performs a POST request to the /api/prescriptions/create endpoint with a JSON payload
     * representing a new prescription. Asserts that the response status is 201 Created and
     * verifies the content of the response using JSON path expressions.
     *
     * @throws Exception if an error occurs while performing the request or processing the response
     */
    @Test
    void testCreatePrescription() throws Exception {
        String jsonRequest = "{"
                + "\"medicineName\": \"VitaminD\","
                + "\"dosage\": \"101mg\","
                + "\"instructions\": \"Dva puta dnevno\","
                + "\"doctor\": {"
                + "    \"id\":" + doctor.getId()
                + "},"
                + "\"patient\": {"
                + "    \"id\":" + patient.getId()
                + "}"
                + "}";

        mockMvc.perform(post("/api/prescriptions/create").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.medicineName").value("VitaminD"))
                .andExpect(jsonPath("$.dosage").value("101mg"))
                .andExpect(jsonPath("$.instructions").value("Dva puta dnevno"))
                .andExpect(jsonPath("$.doctorId").value(doctor.getId())) // Proveri doktor ID
                .andExpect(jsonPath("$.patientId").value(patient.getId()));
    }
}
