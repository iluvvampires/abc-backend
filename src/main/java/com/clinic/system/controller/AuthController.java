package com.clinic.system.controller;

import com.clinic.system.dto.AuthRequest;
import com.clinic.system.dto.AuthResponse;
import com.clinic.system.model.Clinic;
import com.clinic.system.model.User;
import com.clinic.system.repository.ClinicRepository;
import com.clinic.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                Long clinicId = user.getClinic() != null ? user.getClinic().getClinicId() : null;
                return ResponseEntity.ok(new AuthResponse(user.getId(), user.getUsername(), user.getRole(), clinicId));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        // Ensure clinic is set if role is EMPLOYEE
        if (userRequest.getRole() == User.Role.EMPLOYEE && userRequest.getClinic() != null && userRequest.getClinic().getClinicId() != null) {
            Clinic clinic = clinicRepository.findById(userRequest.getClinic().getClinicId()).orElse(null);
            userRequest.setClinic(clinic);
        } else if (userRequest.getRole() == User.Role.ADMIN) {
            userRequest.setClinic(null); // Admins don't strictly need a clinic
        }
        
        userRepository.save(userRequest);
        return ResponseEntity.ok("User registered successfully");
    }
}