package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.repository.FeedbackRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Feedback> getById(Long id) {
        return Optional.of(entityManager.find(Feedback.class, id));
    }

    @Override
    public void save(Feedback feedback) {
        entityManager.persist(feedback);
    }

}
