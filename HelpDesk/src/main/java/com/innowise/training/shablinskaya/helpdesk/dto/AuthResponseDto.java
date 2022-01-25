package com.innowise.training.shablinskaya.helpdesk.dto;

import java.util.Objects;

public class AuthResponseDto {

    private String token;

    private String lastName;

    private String role;

    public AuthResponseDto(){}

    public AuthResponseDto(String token, String lastName, String role) {
        this.token = token;
        this.lastName = lastName;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponseDto that = (AuthResponseDto) o;
        return Objects.equals(token, that.token) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, lastName, role);
    }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "token='" + token + '\'' +
                ", email='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
