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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                Long clinicId = user.getClinic() != null ? user.getClinic().getClinicId() : null;
                AuthResponse response = new AuthResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        clinicId
                );
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> payload) {
        try {
            String username = (String) payload.get("username");
            String password = (String) payload.get("password");
            String roleStr = (String) payload.get("role");

            // Check if user exists
            if (userRepository.findByUsername(username).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            // Create new user
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(User.Role.valueOf(roleStr));

            // Handle clinic for EMPLOYEE
            if ("EMPLOYEE".equals(roleStr) && payload.containsKey("clinicId") && payload.get("clinicId") != null) {
                try {
                    Long clinicId = Long.valueOf(payload.get("clinicId").toString());
                    Optional<Clinic> clinicOpt = clinicRepository.findById(clinicId);
                    if (clinicOpt.isPresent()) {
                        user.setClinic(clinicOpt.get());
                    } else {
                        return ResponseEntity.badRequest().body("Clinic not found with ID: " + clinicId);
                    }
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("Invalid clinic ID format");
                }
            }

            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }
}