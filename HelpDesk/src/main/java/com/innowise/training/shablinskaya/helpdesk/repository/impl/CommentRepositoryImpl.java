package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Comment> getById(Long id) {
        return Optional.of(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAllFromTable() {
        return entityManager.createQuery("SELECT c FROM Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public Comment updateTable(Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public void addToTable(Comment comment) {
        entityManager.persist(comment);

    }
}
