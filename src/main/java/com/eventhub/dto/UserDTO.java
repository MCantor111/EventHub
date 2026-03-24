package com.eventhub.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, LocalDateTime createdAt, List<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}