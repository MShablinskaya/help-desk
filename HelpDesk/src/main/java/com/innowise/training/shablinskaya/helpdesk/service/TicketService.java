package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getAll();

    TicketDto findById(Long id);

    TicketDto findByOwner(Long id);

    List<TicketDto> findByApprove(Long id);

    List<TicketDto> findByAssignee(Long id);

    List<TicketDto> findByState(String state);

    List<TicketDto> findByUrgency(String state);

    void save(TicketDto dto);

    void update(TicketDto dto);
}
