package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;

import java.util.List;

public interface HistoryService  {

    History createTicket(Ticket ticket);

    History getById(Long id);

    List<History> getByTicketId(Long id);
}
