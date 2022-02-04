package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.History;

import java.util.List;

public interface HistoryService  {

    History create(Long ticketId);

    History update(History history);

    History getById(Long id);

    List<History> getByTicketId(Long id);
}
