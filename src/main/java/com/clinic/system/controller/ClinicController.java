package com.clinic.system.controller;

import com.clinic.system.model.Clinic;
import com.clinic.system.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    @Autowired
    private ClinicRepository clinicRepository;

    @GetMapping
    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleClinicStatus(@PathVariable Long id) {
        return clinicRepository.findById(id).map(clinic -> {
            clinic.setIsOpen(!clinic.getIsOpen());
            clinicRepository.save(clinic);
            return ResponseEntity.ok(clinic);
        }).orElse(ResponseEntity.notFound().build());
    }
}
