package com.innowise.training.shablinskaya.helpdesk.entity;


import com.innowise.training.shablinskaya.helpdesk.enums.Role;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ROLE_ID")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Ticket> ticketSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<History> historySet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<Comment> commentSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<Feedback> feedbackSet;


    public User() {
    }

    public User(String firstName, String lastName, Role role, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String userFirstName) {
        this.firstName = userFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String userLastName) {
        this.lastName = userLastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String userEmail) {
        this.email = userEmail;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getRole().equals(user.getRole()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getRole(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", userFirstName='" + firstName + '\'' +
                ", userLastName='" + lastName + '\'' +
                ", userRole='" + role + '\'' +
                ", userEmail='" + email + '\'' +
                ", userPassword='" + password + '\'' +
                '}';
    }


}
