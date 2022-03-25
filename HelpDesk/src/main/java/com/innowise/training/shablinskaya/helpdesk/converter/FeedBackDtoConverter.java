package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class FeedBackDtoConverter {

    private final UserService userService;
    private final TicketService ticketService;
    private final TicketDtoConverter converter;

    @Autowired
    public FeedBackDtoConverter(UserService userService, TicketService ticketService, TicketDtoConverter converter) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.converter = converter;
    }

    public FeedbackDto toDto(Feedback feedback) {
        FeedbackDto dto = new FeedbackDto();

        dto.setId(feedback.getFeedbackId());
        dto.setUserId(feedback.getUserId().getEmail());
        dto.setDate((Timestamp) feedback.getFeedbackDate());
        dto.setRate(feedback.getFeedbackRate());
        dto.setText(feedback.getText());
        dto.setTicketId(feedback.getFeedbackTicketId().getId());

        return dto;
    }

    public Feedback toEntity(FeedbackDto dto) {
        Feedback feedback = new Feedback();

        feedback.setUserId(userService.getCurrentUser());
        feedback.setFeedbackRate(dto.getRate());
        feedback.setFeedbackDate(Timestamp.from(Instant.now()));
        feedback.setText(dto.getText());
        feedback.setTicket(converter.toUpdEntity(ticketService.findById(dto.getTicketId())));

        return feedback;
    }
}
