package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.CommentDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Comment;

public interface CommentConverter {

    CommentDto toDto(Comment comment);

    Comment toEntity(CommentDto commentDto);
}
