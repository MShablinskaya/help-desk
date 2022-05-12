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
    public void remove(Attachment attachment) {
        if (attachment.getId() != null){
            entityManager.remove(attachment);
        }

    }

    @Override
    public Attachment save(Attachment attachment) {
        if (attachment.getId() == null) {
            entityManager.persist(attachment);
        } else {
            entityManager.merge(attachment);
        }
        return attachment;
    }

    @Override
    public List<Attachment> getByTicketId(Long ticketId) {
        return entityManager.createQuery("SELECT a FROM Attachment a WHERE a.ticket.id = :ticketId", Attachment.class)
                .setParameter("ticketId", ticketId)
                .getResultList();
    }
}
