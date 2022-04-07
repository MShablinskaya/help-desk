package com.innowise.training.shablinskaya.helpdesk.dto;

import java.util.List;
import java.util.Objects;

public class AuthResponseDto {

    private String token;

    private String email;

    private List role;

    public AuthResponseDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List getRole() {
        return role;
    }

    public void setRole(List role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponseDto that = (AuthResponseDto) o;
        return Objects.equals(token, that.token)
                && Objects.equals(email, that.email)
                && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, email, role);
    }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", token=" + token +
                '}';
    }
}
