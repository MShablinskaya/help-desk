package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> getById(Long id);

//    List<Ticket> getAll();

    Ticket update(Ticket ticket);

    Ticket create(Ticket ticket);

    //void create(Ticket ticket);

    List<Ticket> getByOwnerId(Long ownerId);

    List<Ticket> getByApproveId(Long approveId);

    List<Ticket> getByAssigneeId(Long assigneeId);

    List<Ticket> getByState(State state);

    List<Ticket> getByUrgency(Urgency urgency);

}
