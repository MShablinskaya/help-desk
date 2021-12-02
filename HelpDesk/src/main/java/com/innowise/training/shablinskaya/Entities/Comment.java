package com.innowise.training.shablinskaya.Entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long commentId;

    @Column(name = "USER_ID", nullable = false)
    private String commentUserId;

    @Column(name = "TEXT", nullable = false)
    private String commentText;

    @Column(name = "TICKET_ID", nullable = false)
    private Long commentTicketId;
}
