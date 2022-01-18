package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.repository.AttachmentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Attachment> getById(Long id) {
        return Optional.of(entityManager.find(Attachment.class, id));
    }

    @Override
    public List<Attachment> getByTicketId(Long id) {
        return null;
    }

    @Override
    public Attachment update(Attachment attachment) {
        return entityManager.merge(attachment);
    }

    @Override
    public void save(Attachment attachment) {
        entityManager.persist(attachment);
    }

}
