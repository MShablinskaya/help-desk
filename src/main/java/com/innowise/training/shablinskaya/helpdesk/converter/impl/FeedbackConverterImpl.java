package com.innowise.training.shablinskaya.helpdesk.converter.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.FeedbackConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class FeedbackConverterImpl implements FeedbackConverter {

    private final UserService userService;
    private final TicketService ticketService;
    private final TicketConverterImpl converter;

    @Autowired
    public FeedbackConverterImpl(UserService userService, TicketService ticketService, TicketConverterImpl converter) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.converter = converter;
    }

    @Override
    public FeedbackDto toDto(Feedback feedback) {
        FeedbackDto dto = new FeedbackDto();

        dto.setRate(feedback.getFeedbackRate());
        dto.setText(feedback.getText());
        dto.setTicketId(feedback.getFeedbackTicketId().getId());

        return dto;
    }

    @Override
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
