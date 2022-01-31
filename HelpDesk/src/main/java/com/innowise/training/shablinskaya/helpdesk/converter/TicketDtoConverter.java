package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Category;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.service.CategoryService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class TicketDtoConverter {
    private UserService userService;
    private CategoryService categoryService;

    @Autowired
    public TicketDtoConverter(UserService userService, CategoryService categoryService){
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public TicketDto toDto(Ticket ticket) {
        TicketDto dto = new TicketDto();

        dto.setName(ticket.getName());
        dto.setDescription(ticket.getDescription());
        dto.setResolutionDate(ticket.getResolutionDate());
        dto.setOwner(ticket.getOwner().getId());
        dto.setAssignee(ticket.getAssignee().getId());
        dto.setApprove(ticket.getApprove().getId());
        dto.setState(ticket.getState().name());
        dto.setCategory(ticket.getCategory().getCategoryId());
        dto.setUrgency(ticket.getUrgency().name());

        return dto;
    }

    public Ticket toEntity(TicketDto dto) {
        Ticket ticket = new Ticket();

        ticket.setName(dto.getName());
        ticket.setDescription(dto.getDescription());
        ticket.setDate(Timestamp.from(Instant.now()));
        ticket.setResolutionDate(dto.getResolutionDate());
        ticket.setOwner(userService.findById(dto.getOwner()));
        ticket.setAssignee(userService.findById(dto.getAssignee()));
        ticket.setApprove(userService.findById(dto.getApprove()));
        ticket.setState(State.valueOf(dto.getState().toUpperCase()));
        ticket.setCategory(categoryService.findById(dto.getCategory()));
        ticket.setUrgency(Urgency.valueOf(dto.getUrgency().toUpperCase()));

        return ticket;

    }
}
