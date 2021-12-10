package com.innowise.training.shablinskaya.Repository.Impl;

import com.innowise.training.shablinskaya.Entity.Comment;
import com.innowise.training.shablinskaya.Repository.CommentRepository;

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
