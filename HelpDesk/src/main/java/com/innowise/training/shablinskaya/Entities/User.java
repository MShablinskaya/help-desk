package com.innowise.training.shablinskaya.Entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Ticket> tickets;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Feedback> feedbacks;
}
