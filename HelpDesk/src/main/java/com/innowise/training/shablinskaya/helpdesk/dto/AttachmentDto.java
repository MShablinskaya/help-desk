package com.innowise.training.shablinskaya.helpdesk.dto;


import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class AttachmentDto {

    private Long id;
    private String Name;
    private Long ticketId;


    public AttachmentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentDto dto = (AttachmentDto) o;
        return Objects.equals(id, dto.id)
                && Objects.equals(Name, dto.Name)
                && Objects.equals(ticketId, dto.ticketId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name, ticketId);
    }

    @Override
    public String toString() {
        return "AttachmentDto{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
