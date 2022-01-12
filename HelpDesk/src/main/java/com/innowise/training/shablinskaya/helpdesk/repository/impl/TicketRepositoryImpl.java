package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
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
    public List<Ticket> getByUserId(Integer userId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.owner = :userId", Ticket.class)
                .setParameter(userId, Ticket.class)
                .getResultList();
    }


    @Override
    public Optional<Ticket> getById(Long id) {
        return Optional.of(entityManager.find(Ticket.class, id));
    }

    @Override
    public List<Ticket> getAllFromTable() {
        return entityManager.createQuery("SELECT t FROM Ticket t", Ticket.class)
                .getResultList();
    }

    @Override
    public Ticket updateTable(Ticket ticket) {
            entityManager.merge(ticket);
        return ticket;
    }


    @Override
    public void addToTable(Ticket ticket) {
        entityManager.persist(ticket);
    }
}
