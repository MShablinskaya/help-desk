package com.innowise.training.shablinskaya.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Blob;
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

    @Column(name = "TICKET_ID", nullable = false)
    private Long attachmentTicketId;

    @Column(name = "NAME", nullable = false)
    private String attachmentName;

    public Attachment(){}

    public Attachment(Long attachmentBlob, Long attachmentTicketId,String attachmentName){
        this.attachmentBlob = attachmentBlob;
        this.attachmentTicketId = attachmentTicketId;
        this.attachmentName = attachmentName;
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

    public Long getAttachmentTicketId() {
        return attachmentTicketId;
    }

    public void setAttachmentTicketId(Long attachmentTicketId) {
        this.attachmentTicketId = attachmentTicketId;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;
        Attachment that = (Attachment) o;
        return getAttachmentBlob().equals(that.getAttachmentBlob()) &&
                getAttachmentTicketId().equals(that.getAttachmentTicketId()) &&
                getAttachmentName().equals(that.getAttachmentName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttachmentBlob(), getAttachmentTicketId(), getAttachmentName());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId=" + attachmentId +
                ", attachmentBlob=" + attachmentBlob +
                ", attachmentTicketId=" + attachmentTicketId +
                ", attachmentName='" + attachmentName + '\'' +
                '}';
    }
}
