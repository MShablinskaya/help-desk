package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.impl.CommentConverterImpl;
import com.innowise.training.shablinskaya.helpdesk.dto.CommentDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.CommentRepository;
import com.innowise.training.shablinskaya.helpdesk.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentConverterImpl converter;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentConverterImpl converter) {
        this.commentRepository = commentRepository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public Comment createComment(CommentDto dto) throws TicketStateException {
        if (dto.getText() != null) {
            return commentRepository.save(converter.toEntity(dto));
        } else {
            throw new TicketStateException("You can't leave empty comment!");
        }
    }
}
