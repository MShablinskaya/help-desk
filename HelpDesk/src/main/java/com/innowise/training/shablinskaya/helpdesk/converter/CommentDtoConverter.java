package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.CommentDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter {
    private UserService userService;
    private TicketService ticketService;
    private TicketDtoConverter converter;

    @Autowired
    public CommentDtoConverter(UserService userService, TicketService ticketService, TicketDtoConverter converter) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.converter = converter;
    }

    public CommentDto toDto(Comment comment){
        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setUserId(comment.getUserId().getId());
        dto.setText(comment.getComment());
        dto.setTicketId(comment.getTicket().getId());

        return dto;
    }

    public Comment toEntity(CommentDto commentDto){
        Comment comment = new Comment();

        comment.setUserId(userService.getCurrentUser());
        comment.setComment(commentDto.getText());
        comment.setTicket(converter.toUpdEntity(ticketService.findById(commentDto.getTicketId())));

        return comment;
    }
}
