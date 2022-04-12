package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

import java.util.List;

public interface TicketService {

    Ticket save(TicketDto dto) throws TicketStateException;

    Ticket changeState(TicketDto ticketDto, State state) throws TicketStateException;

    TicketDto findById(Long id);

    TicketDto ticketStatusChange(Long id, String state) throws TicketStateException;

    TicketDto postNewTicket(String action, TicketDto dto) throws TicketStateException;

    TicketDto editTicket(String action, TicketDto ticketDto) throws TicketStateException;

    List<TicketDto> findAllTicketsByRole() throws TicketStateException;

    List<TicketDto> findByCurrentUser();

    List<String> findAllowedActionsByRole(Long id);
}
