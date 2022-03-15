package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.converter.CommentDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.FeedBackDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.CommentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.CommentService;
import com.innowise.training.shablinskaya.helpdesk.service.FeedbackService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private CommentService commentService;
    private TicketService ticketService;
    private CommentDtoConverter converter;

    @Autowired
    public CommentController(CommentService commentService, TicketService ticketService, CommentDtoConverter converter) {
        this.commentService = commentService;
        this.ticketService = ticketService;
        this.converter = converter;
    }

    @PutMapping("/comment-create/{id}")
    public ResponseEntity<CommentDto> addComment(@PathVariable(name = "id") Long id, @RequestBody CommentDto dto) throws TicketStateException {
        TicketDto ticket = ticketService.findById(id);
        dto.setTicketId(id);

        if (ticket.getId() != null) {
            Comment comment = commentService.createComment(dto);
//            String savedTicketLocation = "tickets/" + ticket.getId();
//            return ResponseEntity.created(URI.create(savedTicketLocation)).build();

            return ResponseEntity.ok(converter.toDto(comment));
        } else {
            throw new EntityNotFoundException("Ticket doesn't exist!");
        }
    }
}
