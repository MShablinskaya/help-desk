package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;


import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> getById(Long id);

    List<Ticket> getAll();

    Ticket update(Ticket ticket);

    void create(Ticket ticket);

    List<Ticket> getByUserId(Long userId);

  //  List<Ticket> getByEmail(String email);

}
