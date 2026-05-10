package com.clinic.system.controller;

import java.util.Map;
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
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");
        String password = (String) payload.get("password");
        String roleStr = (String) payload.get("role");

        // clinicId might be null or a Number
        Long clinicId = null;
        if (payload.get("clinicId") != null) {
            clinicId = Long.valueOf(payload.get("clinicId").toString());
        }

        // Check if user exists
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.Role.valueOf(roleStr));

        // Set clinic if EMPLOYEE and clinicId is provided
        if (user.getRole() == User.Role.EMPLOYEE && clinicId != null) {
            Clinic clinic = clinicRepository.findById(clinicId).orElse(null);
            user.setClinic(clinic);
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}