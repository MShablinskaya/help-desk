package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.History;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository {

    Optional<History> getById(Long id);

    void save(History history);

    List<History> findByTicketId(Long ticketId);
}
