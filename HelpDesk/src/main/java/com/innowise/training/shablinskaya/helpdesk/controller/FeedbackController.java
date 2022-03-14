package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.FeedbackService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {
    private FeedbackService feedbackService;
    private TicketService ticketService;
    private TicketDtoConverter converter;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, TicketService ticketService, TicketDtoConverter converter) {
        this.feedbackService = feedbackService;
        this.ticketService = ticketService;
        this.converter = converter;
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PutMapping("/feedback-create/{id}")
    public ResponseEntity<FeedbackDto> createTicket(@PathVariable(name = "id") Long id, @RequestBody FeedbackDto dto) throws TicketStateException {
        TicketDto ticket = ticketService.findById(id);

        if (ticket.getId() != null && ticket.getState().equals("DONE")) {
            feedbackService.save(converter.toUpdEntity(ticket), dto);
            String savedTicketLocation = "tickets/" + ticket.getId();
            return ResponseEntity.created(URI.create(savedTicketLocation)).build();
        } else {
            throw new EntityNotFoundException("Ticket doesn't exist!");
        }
    }
}
