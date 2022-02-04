package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.repository.HistoryRepository;
import com.innowise.training.shablinskaya.helpdesk.repository.UserRepository;
import com.innowise.training.shablinskaya.helpdesk.service.HistoryService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final static String TICKET_CREATED = "Ticket is created";

    private HistoryRepository historyRepository;
    private UserService userService;
    private TicketService ticketService;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, UserService userService, TicketService ticketService){
        this.historyRepository = historyRepository;
        this.userService= userService;
        this.ticketService = ticketService;
    }


    @Override
    public History create(Long ticketId) {
        History history = new History();
        history.setTicketUploadDate(Timestamp.from(Instant.now()));
        history.setUserId(userService.getCurrentUser());
        history.setActionOnTicket(TICKET_CREATED);
        history.setActionOnTicketDescription(TICKET_CREATED);

        historyRepository.save(history);
        User user = userService.getCurrentUser();
        userService.refresh(user);

        return history;
    }

    @Override
    public History update(History history) {
        return null;
    }

    @Override
    public History getById(Long id) {
        return historyRepository.getById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<History> getByTicketId(Long id) {
        TicketDto ticket = ticketService.findById(id);

        if(ticket != null){
        List<History> histories = historyRepository.findByTicketId(id);
        return histories;}

        return null;
    }
}
