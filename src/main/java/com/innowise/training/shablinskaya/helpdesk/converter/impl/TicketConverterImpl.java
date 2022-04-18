package com.innowise.training.shablinskaya.helpdesk.converter.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.service.CategoryService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class TicketConverterImpl implements TicketConverter {


    private final UserService userService;
    private final CategoryService categoryService;
    private final UserConverter userConverter;


    @Autowired
    public TicketConverterImpl(UserService userService, CategoryService categoryService, UserConverter userConverter) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.userConverter = userConverter;

    }

    @Override
    public TicketDto toDto(Ticket ticket) {
        TicketDto dto = new TicketDto();

        dto.setId(ticket.getId());
        dto.setName(ticket.getName());
        dto.setDescription(ticket.getDescription());
        dto.setCreationDate(ticket.getCreateDate());
        dto.setResolutionDate(ticket.getResolutionDate());
        dto.setOwner(userConverter.toDto(userService.findById(ticket.getOwner().getId())));
        dto.setAssignee(ticket.getAssignee() == null ? null : userConverter.toDto(userService.findById(ticket.getAssignee().getId())));
        dto.setApprove(ticket.getApprove() == null ? null : userConverter.toDto(userService.findById(ticket.getApprove().getId())));
        dto.setState(ticket.getState().name());
        dto.setCategory(ticket.getCategory().getCategoryName());
        dto.setUrgency(ticket.getUrgency().name());


        return dto;
    }

    @Override
    public Ticket toEntity(TicketDto dto) {
        Ticket ticket = new Ticket();

        ticket.setName(dto.getName());
        ticket.setDescription(dto.getDescription());
        ticket.setCreateDate(Timestamp.from(Instant.now()));
        ticket.setResolutionDate(dto.getResolutionDate());
        ticket.setOwner(userService.getCurrentUser());
        ticket.setAssignee(null);
        ticket.setApprove(null);
        ticket.setState(State.valueOf(dto.getState()));
        ticket.setCategory(categoryService.findByName(dto.getCategory()));
        ticket.setUrgency(Urgency.valueOf(dto.getUrgency().toUpperCase()));

        return ticket;

    }

    @Override
    public Ticket toUpdEntity(TicketDto dto) {
        Ticket ticket = new Ticket();

        ticket.setId(dto.getId());
        ticket.setName(dto.getName());
        ticket.setDescription(dto.getDescription());
        ticket.setCreateDate(dto.getCreationDate());
        ticket.setResolutionDate(dto.getResolutionDate());
        ticket.setOwner(userService.findByEmail(dto.getOwner().getEmail()));
        ticket.setAssignee(dto.getAssignee() == null ? null : userService.findByEmail(dto.getAssignee().getEmail()));
        ticket.setApprove(dto.getApprove() == null ? null : userService.findByEmail(dto.getApprove().getEmail()));
        ticket.setState(State.valueOf(dto.getState()));
        ticket.setCategory(categoryService.findByName(dto.getCategory()));
        ticket.setUrgency(Urgency.valueOf(dto.getUrgency().toUpperCase()));

        return ticket;

    }
}
