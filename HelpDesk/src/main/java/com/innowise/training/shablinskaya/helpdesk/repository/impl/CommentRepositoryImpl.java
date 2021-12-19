package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.repository.CommentRepository;

import java.util.List;

public class CommentRepositoryImpl implements CommentRepository {
    @Override
    public Comment getById(Integer id) {
        return null;
    }

    @Override
    public List<Comment> getAllFromTable() {
        return null;
    }

    @Override
    public boolean updateTable(Comment comment) {
        return false;
    }

    @Override
    public boolean deleteFromTable(Integer id) {
        return false;
    }

    @Override
    public void addToTable(Comment comment) {

    }
}
