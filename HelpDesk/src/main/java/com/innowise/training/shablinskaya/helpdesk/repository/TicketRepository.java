package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;


import java.util.List;

public interface TicketRepository extends TableManagerRepository<Ticket, Integer> {
    List<Ticket> getByUserId(Integer userId);

}
