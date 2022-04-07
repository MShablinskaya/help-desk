package com.innowise.training.shablinskaya.helpdesk.converter.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.CommentConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.TicketConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.CommentDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class CommentConverterImpl implements CommentConverter {
    private final UserService userService;
    private final TicketService ticketService;
    private final TicketConverter ticketConverter;
    private final UserConverter userConverter;

    @Autowired
    public CommentConverterImpl(UserService userService, TicketService ticketService, TicketConverter ticketConverter, UserConverter userConverter) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.ticketConverter = ticketConverter;
        this.userConverter = userConverter;
    }

    @Override
    public CommentDto toDto(Comment comment){
        CommentDto dto = new CommentDto();

        dto.setCreationDate(Timestamp.from(Instant.now()));
        dto.setUserId(userConverter.toDto(userService.findById(comment.getUserId().getId())));
        dto.setText(comment.getComment());

        return dto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto){
        Comment comment = new Comment();

        comment.setUserId(userService.getCurrentUser());
        comment.setComment(commentDto.getText());
        comment.setTicket(ticketConverter.toUpdEntity(ticketService.findById(commentDto.getTicketId())));

        return comment;
    }
}
