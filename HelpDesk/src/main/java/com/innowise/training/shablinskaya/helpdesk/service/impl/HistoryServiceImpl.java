package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.repository.HistoryRepository;
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
    private final static String TICKET_EDITED = "Ticket is edited";
    private final static String NEW = "NEW";
    private final static String DRAFT = "DRAFT";

    private HistoryRepository historyRepository;
    private UserService userService;
    private TicketService ticketService;
    private TicketDtoConverter converter;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, UserService userService, TicketService ticketService, TicketDtoConverter converter) {
        this.historyRepository = historyRepository;
        this.userService = userService;
        this.ticketService = ticketService;
        this.converter = converter;
    }


    @Override
    public History create(Long ticketId) {
        Ticket ticket = converter.toEntity(ticketService.findById(ticketId));

        if (ticket != null) {
            History history = new History();
            history.setTicketId(ticket);
            history.setTicketUploadDate(Timestamp.from(Instant.now()));
            history.setUserId(userService.getCurrentUser());
            if (ticket.getState().name().equals(DRAFT)) {
                history.setActionOnTicket(TICKET_CREATED);
                history.setActionOnTicketDescription(TICKET_CREATED);
                return historyRepository.save(history);
            } else if (ticket.getState().name().equals(NEW)) {
                history.setActionOnTicket(TICKET_EDITED);
                history.setActionOnTicketDescription(TICKET_EDITED);
                return historyRepository.save(history);
            }
        }

        return null;
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

        if (ticket != null) {
            return historyRepository.findByTicketId(id);
        }

        return null;
    }
}
