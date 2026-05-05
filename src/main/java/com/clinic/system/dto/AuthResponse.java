package com.clinic.system.dto;

import com.clinic.system.model.User.Role;

public class AuthResponse {
    private Long id;
    private String username;
    private Role role;
    private Long clinicId;

    public AuthResponse(Long id, String username, Role role, Long clinicId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.clinicId = clinicId;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public Long getClinicId() { return clinicId; }
}
