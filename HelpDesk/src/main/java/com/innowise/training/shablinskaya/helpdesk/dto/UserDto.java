package com.innowise.training.shablinskaya.helpdesk.dto;

import com.innowise.training.shablinskaya.helpdesk.enums.Role;

import java.util.Objects;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private String password;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, Role role, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return getFirstName().equals(userDto.getFirstName()) &&
                getLastName().equals(userDto.getLastName()) &&
                getRole().equals(userDto.getRole()) &&
                getEmail().equals(userDto.getEmail()) &&
                getPassword().equals(userDto.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getRole(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
