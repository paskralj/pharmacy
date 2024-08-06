package com.prescriptions.taks.prescriptions.integration;

import com.prescriptions.taks.prescriptions.entities.Doctor;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test class for testing user registration endpoints.
 * This class tests the registration functionality for both doctors and patients
 * using mock MVC to simulate HTTP requests and validate responses.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    /**
     * Set up the test environment by clearing existing data in repositories.
     * This method is called before each test method is executed.
     */
    @BeforeEach
    void setUp(){
        prescriptionRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
    }

     /**
     * Tests the registration of a doctor.
     * Sends a POST request to the /api/users/register/doctor endpoint with a JSON payload
     * and validates the response status and content.
     *
     * @throws Exception if any error occurs during the request or response handling
     */
    @Test
    void testRegisterDoctor() throws Exception{
        String jsonRequest = "{"
                + "\"username\": \"test1\","
                + "\"password\": \"test2\","
                + "\"role\": \"DOCTOR\","
                + "\"specialty\": \"neurokirurg\""
                + "}";

        mockMvc.perform(post("/api/users/register/doctor").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("test1"))
                .andExpect(jsonPath("$.specialty").value("neurokirurg"));

    }

    /**
     * Tests the registration of a patient.
     * Sends a POST request to the /api/users/register/patient endpoint with a JSON payload
     * and validates the response status and content.
     *
     * @throws Exception if any error occurs during the request or response handling
     */
    @Test
    void testRegisterPatient() throws Exception{
        String jsonRequest = "{"
                + "\"username\": \"pacijent1\","
                + "\"password\": \"password123\","
                + "\"medicalHistory\": \"Nema značajnih bolesti.\""
                + "}";

        mockMvc.perform(post("/api/users/register/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("pacijent1"));

    }
}