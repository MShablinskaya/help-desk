package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.repository.HistoryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<History> findByTicketId(Long ticketId) {
        return entityManager.createQuery("SELECT h FROM History h WHERE h.ticket.id = :ticketId", History.class)
                .setParameter("ticketId", History.class)
                .getResultList();
    }

    @Override
    public Optional<History> getById(Long id) {
        return Optional.of(entityManager.find(History.class, id));
    }


    @Override
    public History save(History history) {
        if(history.getId() == null){
        entityManager.persist(history);}
        else {
            entityManager.merge(history);
        }
        return history;
    }


}
