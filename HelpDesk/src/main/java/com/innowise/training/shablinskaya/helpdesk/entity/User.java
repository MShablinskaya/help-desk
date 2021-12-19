package com.innowise.training.shablinskaya.helpdesk.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "ROLE_ID", nullable = false)
    private String userRole;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Ticket> ticketSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<History> historySet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Comment> commentSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Feedback> feedbackSet;


    public User() {
    }

    public User(String firstName, String lastName, String userRole, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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

    public void setPassword(String userPassword) {
        this.password = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getUserRole().equals(user.getUserRole()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getUserRole(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", userFirstName='" + firstName + '\'' +
                ", userLastName='" + lastName + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userEmail='" + email + '\'' +
                ", userPassword='" + password + '\'' +
                '}';
    }


}
