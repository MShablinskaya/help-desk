package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.repository.TicketRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ticket> getByOwnerId(int userId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.ownerId = :userId", Ticket.class)
                .setParameter(userId, Ticket.class)
                .getResultList();
    }

//    @Override
//    public List<Ticket> getByAssigneeId(int userId) {
//        return null;
//    }
//
//    @Override
//    public List<Ticket> getByApproveId(int userId) {
//        return null;
//    }

    @Override
    public Optional<Ticket> getById(Integer id) {
        return Optional.of(entityManager.createQuery("SELECT t FROM Ticket t WHERE t.ticketId = :id", Ticket.class)
                .setParameter(id, Ticket.class)
                .getSingleResult());
    }

    @Override
    public List<Ticket> getAllFromTable() {
        return entityManager.createQuery("SELECT t FROM Ticket t", Ticket.class)
                .getResultList();
    }

    @Override
    public Ticket updateTable(Ticket ticket) {
        if (ticket.getTicketId() != null)
            entityManager.merge(ticket);

        return ticket;
    }

    @Override
    public void deleteFromTable(Ticket ticket) {
        Ticket mergedTicket = entityManager.merge(ticket);
        entityManager.remove(mergedTicket);
    }

    @Override
    public void addToTable(Ticket ticket) {
        if (ticket.getTicketId() == null) {
            entityManager.persist(ticket);
        }

    }
}
