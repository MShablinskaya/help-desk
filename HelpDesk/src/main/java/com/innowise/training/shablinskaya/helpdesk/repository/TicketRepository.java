package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;


import java.util.List;

public interface TicketRepository extends TableManagerRepository<Ticket, Integer> {
    List<Ticket> getByOwnerId(int userId);
//    List<Ticket> getByAssigneeId(int userId);
//    List<Ticket> getByApproveId(int userId);
}
