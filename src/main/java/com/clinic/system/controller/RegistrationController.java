package com.clinic.system.controller;

import com.clinic.system.dto.RegistrationRequest;
import com.clinic.system.model.AnimalCondition;
import com.clinic.system.model.Clinic;
import com.clinic.system.model.Exposure;
import com.clinic.system.model.Patient;
import com.clinic.system.repository.ClinicRepository;
import com.clinic.system.repository.ExposureRepository;
import com.clinic.system.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ExposureRepository exposureRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<?> registerPatient(@RequestBody RegistrationRequest req) {
        try {
            // Debug logging
            System.out.println("=== REGISTRATION REQUEST ===");
            System.out.println("First Name: " + req.getFirstName());
            System.out.println("Last Name: " + req.getLastName());
            System.out.println("Clinic ID: " + req.getClinicId());
            System.out.println("Exposure Date: " + req.getExposureDate());
            
            // 1. Clinic Validation
            Clinic clinic = clinicRepository.findById(req.getClinicId()).orElse(null);
            if (clinic == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "Clinic not found"));
            }
            if (!clinic.getIsOpen()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Sorry, this clinic is currently closed."));
            }

            // 2. Create and Save Patient FIRST
            Patient patient = new Patient();
            patient.setFirstName(req.getFirstName());
            patient.setMiddleName(req.getMiddleName());
            patient.setLastName(req.getLastName());
            patient.setBirthdate(req.getBirthdate());
            patient.setGender(req.getGender());
            patient.setContactNumber(req.getContactNumber());
            patient.setRegion(req.getRegion());
            patient.setProvince(req.getProvince());
            patient.setCity(req.getCity());
            patient.setBarangay(req.getBarangay());
            patient.setZone(req.getZone() != null ? req.getZone() : "URBAN");
            patient.setStreetAddress(req.getStreetAddress());
            
            // IMPORTANT: Save patient first to generate ID
            Patient savedPatient = patientRepository.save(patient);
            System.out.println("Saved Patient ID: " + savedPatient.getPatientId());
            
            // 3. Create Exposure with the SAVED patient ID
            Exposure exp = new Exposure();
            exp.setPatient(savedPatient);  // Use the saved patient with generated ID
            exp.setClinic(clinic);
            
            // Set exposure date
            LocalDate exposureDate = req.getExposureDate();
            if (exposureDate == null) {
                exposureDate = LocalDate.now();
            }
            exp.setExposureDate(exposureDate);
            
            // Set other exposure fields
            exp.setAnimalType(req.getAnimalType() != null ? req.getAnimalType() : "Unknown");
            exp.setExposureType(req.getExposureType() != null ? req.getExposureType() : "Bite");
            exp.setPlaceOfExposure(req.getPlaceOfExposure() != null ? req.getPlaceOfExposure() : "Not specified");

            exp.setBiteCategory(null); // Initially null until assessed
            
            // Save exposure
            Exposure savedExposure = exposureRepository.save(exp);
            System.out.println("Saved Exposure ID: " + savedExposure.getExposureId());
            System.out.println("Exposure Patient ID: " + savedExposure.getPatient().getPatientId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient registered successfully");
            response.put("patientId", savedPatient.getPatientId());
            response.put("exposureId", savedExposure.getExposureId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(Map.of("message", "Registration failed: " + e.getMessage()));
        }
    }
}