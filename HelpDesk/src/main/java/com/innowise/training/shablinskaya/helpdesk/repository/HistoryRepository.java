package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.History;

import java.util.List;

public interface HistoryRepository extends TableManagerRepository<History, Integer> {

    List<History> findByTicketId(int ticketId);
}
