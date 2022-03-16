package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

import java.util.List;

public interface HistoryService {

    History createTicketHistory(Ticket ticket) throws TicketStateException;

    History ticketHistoryForEdit(Ticket ticket) throws TicketStateException;

    History getById(Long id);

    List<History> getByTicketId(Long id);
}
