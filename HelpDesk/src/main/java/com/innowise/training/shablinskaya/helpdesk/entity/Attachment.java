package com.innowise.training.shablinskaya.helpdesk.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "Attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long attachmentId;

    @Column(name = "BLOB")
    private Long attachmentBlob;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;

    @Column(name = "NAME")
    private String name;




    public Attachment(){}

    public Attachment(Long attachmentBlob, Ticket ticket, String name){
        this.attachmentBlob = attachmentBlob;
        this.ticket = ticket;
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket attachmentTicketId) {
        this.ticket = ticket;
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
                getTicket().equals(that.getTicket()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttachmentBlob(), getTicket(), getName());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId=" + attachmentId +
                ", attachmentBlob=" + attachmentBlob +
                ", attachmentTicketId=" + ticket +
                ", attachmentName='" + name + '\'' +
                '}';
    }
}
