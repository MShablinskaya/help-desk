package com.innowise.training.shablinskaya.Entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ATTACHMENT")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long attachmentId;

    @Column(name = "BLOB", nullable = false)
    private Blob attachmentBlob;

    @Column(name = "TICKET_ID", nullable = false)
    private Long attachmentTicketId;

    @Column(name = "NAME", nullable = false)
    private String attachmentName;
}
