package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

import java.util.List;

public interface TicketService {

    //List<TicketDto> getAll();

    TicketDto findById(Long id);

    List<TicketDto> findByOwner(Long id);

    List<TicketDto> findByApprove(Long id);

    List<TicketDto> findByAssignee(Long id);

    List<TicketDto> findByState(State state);

    List<TicketDto> findByUrgency(Urgency urgency);

    Ticket save(TicketDto dto);

    Ticket changeState(TicketDto ticketDto, State state) throws TicketStateException;
}
