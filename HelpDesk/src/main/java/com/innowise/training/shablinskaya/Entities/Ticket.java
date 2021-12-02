package com.innowise.training.shablinskaya.Entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long ticketId;

    @Column(name = "NAME", nullable = false)
    private String ticketName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String ticketDescription;

    @Column(name = "CREATED_ON", nullable = false)
    private Date ticketDate;

    @Column(name = "DESIRED_RESOLUTION_DATE", nullable = false)
    private Date ticketResolutionDate;

    @Column(name = "ASSIGNEE_ID", nullable = false)
    private Long assigneeId;

    @Column(name = "OWNER_ID", nullable = false)
    private Long ownerId;

    @Column(name = "STATE_ID", nullable = false)
    private String stateId;

    @Column(name = "CATEGORY_ID", nullable = false)
    private Long ticketCategoryId;

    @Column(name = "URGENCY_ID", nullable = false)
    private String urgencyId;

    @Column(name = "APPROVE_ID", nullable = false)
    private Long approveId;

}
