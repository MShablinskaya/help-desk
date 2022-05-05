package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Comment;
import com.innowise.training.shablinskaya.helpdesk.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Comment> getById(Long id) {
        return Optional.of(entityManager.find(Comment.class, id));
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
        } else {
            entityManager.merge(comment);
        }
        return comment;

    }

    @Override
    public List<Comment> getCommentsByTicketId(Long ticketId) {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.ticket.id = :ticketId", Comment.class)
                .setParameter("ticketId", ticketId)
                .getResultList();
    }

}
