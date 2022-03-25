package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.converter.FeedBackDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.FeedbackService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {
    private final static String DONE = "DONE";
    private final FeedbackService feedbackService;
    private final TicketService ticketService;
    private final FeedBackDtoConverter converter;
    private final UserService userService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, TicketService ticketService, FeedBackDtoConverter converter, UserService userService) {
        this.feedbackService = feedbackService;
        this.ticketService = ticketService;
        this.converter = converter;
        this.userService = userService;
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PutMapping("/feedback-create/{id}")
    public ResponseEntity<FeedbackDto> createFeedback(@PathVariable(name = "id") Long id, @RequestBody FeedbackDto dto) throws TicketStateException {
        TicketDto ticket = ticketService.findById(id);
        dto.setTicketId(id);

        if (ticket.getId() != null && ticket.getState().equals(DONE) && ticket.getOwner().equals(userService.getCurrentUser().getEmail())) {
            Feedback feedback = feedbackService.save(dto);
//            String savedTicketLocation = "tickets/" + ticket.getId();
//            return ResponseEntity.created(URI.create(savedTicketLocation)).build();

            return ResponseEntity.ok(converter.toDto(feedback));
        } else {
            throw new EntityNotFoundException("Ticket doesn't exist!");
        }
    }
}
