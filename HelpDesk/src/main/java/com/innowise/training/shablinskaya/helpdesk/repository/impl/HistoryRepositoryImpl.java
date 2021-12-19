package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.repository.HistoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<History> findByTicketId(int ticketId) {
        return entityManager.createQuery("SELECT h FROM History h WHERE h.historyTicketId = :ticketId", History.class)
                .setParameter(ticketId, History.class)
                .getResultList();
    }

    @Override
    public Optional<History> getById(Integer id) {
        return Optional.of(entityManager.createQuery("SELECT h FROM History h WHERE h.historyId = :id", History.class)
        .setParameter(id, History.class)
        .getSingleResult());
    }

    @Override
    public List<History> getAllFromTable() {
        return entityManager.createQuery("SELECT h FROM History h", History.class)
                .getResultList();
    }

    @Override
    public History updateTable(History history) {
        if (history.getHistoryId() != null)
            entityManager.merge(history);

        return history;
    }

    @Override
    public void deleteFromTable(History history) {
        History mergedHistory = entityManager.merge(history);
        entityManager.remove(mergedHistory);

    }

    @Override
    public void addToTable(History history) {
        if (history.getHistoryId() == null) {
            entityManager.persist(history);
        }

    }
}
