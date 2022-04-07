package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.CommentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

public interface CommentService {

    Comment saveComment(CommentDto dto, TicketDto ticketDto) throws TicketStateException;

    CommentDto postComment(Long id, CommentDto commentDto) throws TicketStateException;
}
