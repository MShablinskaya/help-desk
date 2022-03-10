package com.innowise.training.shablinskaya.helpdesk.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import jakarta.validation.constraints.Pattern;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ROLE_ID")
    @Enumerated(EnumType.STRING)
    private Role roleId;

    @Column(name = "EMAIL")
    @Pattern(regexp = ".+@.+\\.[a-z]", message = "Invalid Email address!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @Column(name = "PASSWORD")
    @Pattern(regexp = "^(&=.*\\d).{6,20}$", flags = Pattern.Flag.UNICODE_CASE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    public User(Long id, String firstName, String lastName, Role roleId, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
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

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role role) { this.roleId = role;
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

    public Set<Ticket> getTicketSet() {
        return ticketSet;
    }

    public void setTicketSet(Set<Ticket> ticketSet) {
        this.ticketSet = ticketSet;
    }

    public Set<History> getHistorySet() {
        return historySet;
    }

    public void setHistorySet(Set<History> historySet) {
        this.historySet = historySet;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    public Set<Feedback> getFeedbackSet() {
        return feedbackSet;
    }

    public void setFeedbackSet(Set<Feedback> feedbackSet) {
        this.feedbackSet = feedbackSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getRoleId().equals(user.getRoleId()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getRoleId(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", userFirstName='" + firstName + '\'' +
                ", userLastName='" + lastName + '\'' +
                ", userRole='" + roleId + '\'' +
                ", userEmail='" + email + '\'' +
                ", userPassword='" + password + '\'' +
                '}';
    }


}
