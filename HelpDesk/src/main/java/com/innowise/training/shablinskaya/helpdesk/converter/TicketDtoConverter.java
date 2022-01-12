package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoConverter {

    public TicketDto toDto(Ticket ticket){
        TicketDto dto = new TicketDto();

        dto.setId(ticket.getId());
        dto.setName(ticket.getName());
        dto.setDescription(ticket.getDescription());
        dto.setDate(ticket.getDate());
        dto.setResolutionDate(ticket.getResolutionDate());
        dto.setOwner(ticket.getOwner());
        dto.setAssignee(ticket.getAssignee());
        dto.setApprove(ticket.getApprove());
        dto.setState(ticket.getState());
        dto.setCategory(ticket.getCategory());
        dto.setUrgency(ticket.getUrgency());

        return dto;
    }

    public Ticket toEntity(TicketDto dto){
        Ticket ticket = new Ticket();

        ticket.setName(dto.getName());
        ticket.setDescription(dto.getDescription());
        ticket.setDate(dto.getDate());
        ticket.setResolutionDate(dto.getResolutionDate());
        ticket.setOwner(dto.getOwner());
        ticket.setAssignee(dto.getAssignee());
        ticket.setApprove(dto.getApprove());
        ticket.setState(dto.getState());
        ticket.setCategory(dto.getCategory());
        ticket.setUrgency(dto.getUrgency());

        return ticket;

    }
}
