package com.innowise.training.shablinskaya.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "HISTORY")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long historyId;

    @Column(name = "TICKET_ID", nullable = false)
    private Long historyTicketId;

    @Column(name = "DATE", nullable = false)
    private Date ticketUploadDate;

    @Column(name = "ACTION", nullable = false)
    private String actionOnTicket;

    @Column(name = "USER_ID", nullable = false)
    private Long historyUserId;

    @Column(name = "DESCRIPTION", nullable = false)
    private String actionOnTicketDescription;
}
