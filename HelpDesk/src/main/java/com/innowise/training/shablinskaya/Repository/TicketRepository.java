package com.innowise.training.shablinskaya.Repository;

import com.innowise.training.shablinskaya.Entity.Ticket;
import com.innowise.training.shablinskaya.Entity.User;

import java.util.List;

public interface TicketRepository extends TableManagerRepository<Ticket, Integer> {
    List<Ticket> getByUser(User user);
}
