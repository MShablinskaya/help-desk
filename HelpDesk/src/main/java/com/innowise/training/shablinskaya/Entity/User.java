package com.innowise.training.shablinskaya.Entity;

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
    private Long userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String userFirstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String userLastName;

    @Column(name = "ROLE_ID", nullable = false)
    private String userRole;

    @Column(name = "EMAIL", nullable = false)
    private String userEmail;

    @Column(name = "PASSWORD", nullable = false)
    private String userPassword;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Ticket> tickets;

    public User() {
    }

    public User(String userFirstName, String userLastName, String userRole, String userEmail, String userPassword) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userRole = userRole;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserFirstName().equals(user.getUserFirstName()) &&
                getUserLastName().equals(user.getUserLastName()) &&
                getUserRole().equals(user.getUserRole()) &&
                getUserEmail().equals(user.getUserEmail()) &&
                getUserPassword().equals(user.getUserPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserFirstName(), getUserLastName(), getUserRole(), getUserEmail(), getUserPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
