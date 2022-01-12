package com.innowise.training.shablinskaya.helpdesk.dto;


import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;

import java.util.Objects;

public class AttachmentDto {

    private Long id;
    private Long blob;
    private Ticket ticketId;
    private String name;

    public AttachmentDto(){}

    public AttachmentDto(Long id, Long blob, Ticket ticketId, String name) {
        this.id = id;
        this.blob = blob;
        this.ticketId = ticketId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlob() {
        return blob;
    }

    public void setBlob(Long blob) {
        this.blob = blob;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public void setTicketId(Ticket ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentDto that = (AttachmentDto) o;
        return id.equals(that.id) &&
                Objects.equals(blob, that.blob) &&
                Objects.equals(ticketId, that.ticketId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, blob, ticketId, name);
    }

    @Override
    public String toString() {
        return "AttachmentDto{" +
                "id=" + id +
                ", blob=" + blob +
                ", ticketId=" + ticketId +
                ", name='" + name + '\'' +
                '}';
    }
}
