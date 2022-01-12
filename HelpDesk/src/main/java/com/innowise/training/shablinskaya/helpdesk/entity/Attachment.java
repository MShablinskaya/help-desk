package com.innowise.training.shablinskaya.helpdesk.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

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
    private Long attachmentBlob;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "TICKET_ID", nullable = false)
    private Ticket ticketId;

    @Column(name = "NAME", nullable = false)
    private String name;




    public Attachment(){}

    public Attachment(Long attachmentBlob, Ticket ticketId, String name){
        this.attachmentBlob = attachmentBlob;
        this.ticketId = ticketId;
        this.name = name;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getAttachmentBlob() {
        return attachmentBlob;
    }

    public void setAttachmentBlob(Long attachmentBlob) {
        this.attachmentBlob = attachmentBlob;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public void setTicketId(Ticket attachmentTicketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String attachmentName) {
        this.name = attachmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;
        Attachment that = (Attachment) o;
        return getAttachmentBlob().equals(that.getAttachmentBlob()) &&
                getTicketId().equals(that.getTicketId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttachmentBlob(), getTicketId(), getName());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId=" + attachmentId +
                ", attachmentBlob=" + attachmentBlob +
                ", attachmentTicketId=" + ticketId +
                ", attachmentName='" + name + '\'' +
                '}';
    }
}
