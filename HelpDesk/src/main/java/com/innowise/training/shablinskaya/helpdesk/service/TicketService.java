package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getAll();
    TicketDto findById(Long id);
    void save(TicketDto dto);
}
