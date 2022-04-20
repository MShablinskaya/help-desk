package com.innowise.training.shablinskaya.helpdesk.repository.impl;


import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.repository.TicketRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ticket> getByOwnerId(Long ownerId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.owner.id = :ownerId", Ticket.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
    }

    @Override
    public List<Ticket> getByApproveId(Long approveId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.approve.id =: approveId", Ticket.class)
                .setParameter("approveId", approveId)
                .getResultList();
    }

    @Override
    public List<Ticket> getByAssigneeId(Long assigneeId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.assignee.id =:assigneeId", Ticket.class)
                .setParameter("assigneeId", assigneeId)
                .getResultList();
    }

    @Override
    public List<Ticket> getByState(State state) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.state  = :state")
                .setParameter("state", state)
                .getResultList();
    }


    @Override
    public List<Ticket> getByUrgency(Urgency urgency) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.urgency = :urgency")
                .setParameter("urgency", urgency)
                .getResultList();
    }


    @Override
    public Optional<Ticket> getById(Long id) {
        return Optional.of(entityManager.find(Ticket.class, id));
    }

    @Override
    public Ticket update(Ticket ticket) {
        entityManager.merge(ticket);
        return ticket;
    }

    @Override
    public Ticket create(Ticket ticket) {

        if (ticket.getId() == null) {
            entityManager.persist(ticket);
        } else {
            entityManager.merge(ticket);
        }
        return ticket;
    }

    public void getPagination() {
        int pageNumber = 1;
        int pageSize = 5;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Ticket.class)));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> from = criteriaQuery.from(Ticket.class);
        CriteriaQuery<Ticket> select = criteriaQuery.select(from);

        TypedQuery<Ticket> typedQuery = entityManager.createQuery(select);
        while (pageNumber < count.intValue()) {
            typedQuery.setFirstResult(pageNumber - 1);
            typedQuery.setMaxResults(pageSize);
            pageNumber += pageSize;
        }


    }

}
