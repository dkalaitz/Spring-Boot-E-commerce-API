package com.example.eshop.dto.user;

public class UserRequest {
    private String username;
    private String email;

    // Getters and setters (or lombok annotations for simplicity)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
