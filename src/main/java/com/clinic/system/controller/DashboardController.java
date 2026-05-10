package com.clinic.system.controller;

import com.clinic.system.model.AnimalCondition;
import com.clinic.system.dto.AssessmentRequest;
import com.clinic.system.model.Exposure;
import com.clinic.system.model.Patient;
import com.clinic.system.repository.ExposureRepository;
import com.clinic.system.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ExposureRepository exposureRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/exposures/clinic/{clinicId}")
    public ResponseEntity<?> getExposuresByClinic(@PathVariable Long clinicId) {
        try {
            List<Exposure> exposures = exposureRepository.findByClinic_ClinicId(clinicId);

            System.out.println("Found " + exposures.size() + " exposures for clinic " + clinicId);

            for (Exposure exp : exposures) {
                System.out.println("Exposure ID: " + exp.getExposureId() +
                        ", Patient: " + (exp.getPatient() != null ? exp.getPatient().getFirstName() : "NULL") +
                        ", Patient ID: " + (exp.getPatient() != null ? exp.getPatient().getPatientId() : "NULL"));
            }

            return ResponseEntity.ok(exposures);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/exposures")
    public ResponseEntity<List<Exposure>> getAllExposures() {
        return ResponseEntity.ok(exposureRepository.findAll());
    }

    @DeleteMapping("/exposures/{id}")
    public ResponseEntity<?> deleteExposure(@PathVariable Long id) {
        return exposureRepository.findById(id).map(exp -> {
            exposureRepository.delete(exp);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        return patientRepository.findById(id).map(patient -> {
            patient.setFirstName(updatedPatient.getFirstName());
            patient.setMiddleName(updatedPatient.getMiddleName());
            patient.setLastName(updatedPatient.getLastName());
            patient.setContactNumber(updatedPatient.getContactNumber());
            patient.setRegion(updatedPatient.getRegion());
            patient.setProvince(updatedPatient.getProvince());
            patient.setCity(updatedPatient.getCity());
            patient.setBarangay(updatedPatient.getBarangay());
            patient.setZone(updatedPatient.getZone());
            patient.setStreetAddress(updatedPatient.getStreetAddress());
            patientRepository.save(patient);
            return ResponseEntity.ok(patient);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PatchMapping("/patients/{id}/assess")
    public ResponseEntity<?> assessPatient(@PathVariable Long id, @RequestBody AssessmentRequest assessmentData) {
        // FIX: Find exposure by patient ID - returns a List, not Optional
        List<Exposure> exposures = exposureRepository.findByPatient_PatientId(id);

        if (exposures.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Get the first exposure (or you might want the most recent one)
        Exposure exposure = exposures.get(0);

        try {
            // 1. Update the Exposure object with assessment details
            if (assessmentData.getBiteCategory() != null) {
                exposure.setBiteCategory(assessmentData.getBiteCategory());
            }
            if (assessmentData.getExposureDate() != null) {
                exposure.setExposureDate(assessmentData.getExposureDate());
            }
            if (assessmentData.getAnimalType() != null) {
                exposure.setAnimalType(assessmentData.getAnimalType());
            }
            if (assessmentData.getExposureType() != null) {
                exposure.setExposureType(assessmentData.getExposureType());
            }
            if (assessmentData.getPlaceOfExposure() != null) {
                exposure.setPlaceOfExposure(assessmentData.getPlaceOfExposure());
            }

            // 2. Get the patient
            Patient patient = exposure.getPatient();

            // 3. Handle Animal Conditions
            if (assessmentData.getAnimalConditions() != null) {
                patient.getAnimalConditions().clear();
                for (String conditionStr : assessmentData.getAnimalConditions()) {
                    AnimalCondition ac = new AnimalCondition();
                    ac.setConditionName(conditionStr);
                    ac.setPatient(patient);
                    ac.setExposure(exposure);
                    patient.getAnimalConditions().add(ac);
                }
            }

            // 4. Save both entities
            exposureRepository.save(exposure);
            patientRepository.save(patient);

            return ResponseEntity.ok("Assessment successful");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Assessment failed: " + e.getMessage());
        }
    }
}