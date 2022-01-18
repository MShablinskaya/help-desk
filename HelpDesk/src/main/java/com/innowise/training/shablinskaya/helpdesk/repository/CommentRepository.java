package com.innowise.training.shablinskaya.helpdesk.repository;


import com.innowise.training.shablinskaya.helpdesk.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository{
    Optional<Comment> getById(Long id);

    void saveToTable(Comment comment);

    //getByTicketId
}
