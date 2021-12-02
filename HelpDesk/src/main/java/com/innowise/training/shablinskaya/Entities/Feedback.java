package com.innowise.training.shablinskaya.Entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long feedbackId;

    @Column(name = "USER_ID", nullable = false)
    private Long feedbackUserId;

    @Column(name = "RATE", nullable = false)
    private Long feedbackRate;

    @Column(name = "DATE", nullable = false)
    private Date feedbackDate;

    @Column(name = "TEXT", nullable = false)
    private String feedbacktext;

    @Column(name = "TICKET_ID", nullable = false)
    private Long feedbackTicketId;
}
