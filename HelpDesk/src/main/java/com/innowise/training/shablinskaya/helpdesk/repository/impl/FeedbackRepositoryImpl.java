package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.repository.FeedbackRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class FeedbackRepositoryImpl implements FeedbackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Feedback> getById(Long id) {
        return Optional.of(entityManager.find(Feedback.class, id));
    }

    @Override
    public List<Feedback> getAllFromTable() {
        return entityManager.createQuery("SELECT f FROM Feedback f", Feedback.class)
                .getResultList();
    }

    @Override
    public Feedback updateTable(Feedback feedback) {
        return entityManager.merge(feedback);
    }

    @Override
    public void addToTable(Feedback feedback) {
        entityManager.persist(feedback);
    }
}
