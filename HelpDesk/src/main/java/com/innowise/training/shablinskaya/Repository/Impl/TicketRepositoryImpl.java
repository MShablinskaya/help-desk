package com.innowise.training.shablinskaya.Repository.Impl;

import com.innowise.training.shablinskaya.Entity.Ticket;
import com.innowise.training.shablinskaya.Entity.User;
import com.innowise.training.shablinskaya.Repository.TicketRepository;

import java.util.List;

public class TicketRepositoryImpl implements TicketRepository {
    @Override
    public List<Ticket> getByUser(User user) {
        return null;
    }

    @Override
    public Ticket getById(Integer iв) {
        return null;
    }

    @Override
    public List<Ticket> getAllFromTable() {
        return null;
    }

    @Override
    public boolean updateTable(Ticket ticket) {
        return false;
    }

    @Override
    public boolean deleteFromTable(Integer id) {
        return false;
    }

    @Override
    public void addToTable(Ticket ticket) {

    }
}
