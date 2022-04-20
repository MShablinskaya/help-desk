package com.innowise.training.shablinskaya.helpdesk.repository;

import com.gargoylesoftware.htmlunit.Page;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> getById(Long id);

    Ticket update(Ticket ticket);

    Ticket create(Ticket ticket);

    List<Ticket> getByOwnerId(Long ownerId);

    List<Ticket> getByApproveId(Long approveId);

    List<Ticket> getByAssigneeId(Long assigneeId);

    List<Ticket> getByState(State state);

    List<Ticket> getByUrgency(Urgency urgency);


}
