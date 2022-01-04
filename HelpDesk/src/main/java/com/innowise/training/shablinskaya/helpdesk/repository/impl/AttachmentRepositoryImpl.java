package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.repository.AttachmentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class AttachmentRepositoryImpl implements AttachmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Attachment> getById(Long id) {
        return Optional.of(entityManager.find(Attachment.class, id));
    }

    @Override
    public List<Attachment> getAllFromTable() {
        return entityManager.createQuery("SELECT a FROM Attachment a", Attachment.class)
                .getResultList();
    }

    @Override
    public Attachment updateTable(Attachment attachment) {
        return entityManager.merge(attachment);
    }

    @Override
    public void addToTable(Attachment attachment) {
        entityManager.persist(attachment);

    }
}
