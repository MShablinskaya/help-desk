package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.CommentConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.impl.CommentConverterImpl;
import com.innowise.training.shablinskaya.helpdesk.dto.CommentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.CommentRepository;
import com.innowise.training.shablinskaya.helpdesk.service.CommentService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentConverter converter;
    private final TicketService ticketService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentConverter converter,
                              TicketService ticketService) {
        this.commentRepository = commentRepository;
        this.converter = converter;
        this.ticketService = ticketService;
    }

    @Override
    @Transactional
    public CommentDto postComment(Long id, CommentDto commentDto) throws TicketStateException {
        TicketDto ticketDto = ticketService.findById(id);
        return converter.toDto(saveComment(commentDto, ticketDto));
    }

    @Override
    @Transactional
    public Comment saveComment(CommentDto dto, TicketDto ticketDto) throws TicketStateException {
        if (ticketDto != null && dto.getText() != null) {
            dto.setTicketId(ticketDto.getId());
            return commentRepository.save(converter.toEntity(dto));
        } else {
            throw new TicketStateException("You can't leave empty comment!");
        }
    }

}
