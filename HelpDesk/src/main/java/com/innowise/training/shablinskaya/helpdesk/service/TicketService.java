package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;

import java.util.List;

public interface TicketService {

    //List<TicketDto> getAll();

    TicketDto findById(Long id);

    List<TicketDto> findByOwner(Long id);

    List<TicketDto> findByApprove(Long id);

    List<TicketDto> findByAssignee(Long id);

    List<TicketDto> findByState(String state);

    List<TicketDto> findByUrgency(String urgency);

    void save(TicketDto dto);

    void update(TicketDto dto);
}
