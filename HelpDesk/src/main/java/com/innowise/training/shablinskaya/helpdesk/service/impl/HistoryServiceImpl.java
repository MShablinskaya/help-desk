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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final static String TICKET_CREATED = "Ticket is created";
    private final static String TICKET_CHANGED = "Ticket Status is changed";
    private final static String DRAFT_TO_NEW = "Ticket Status is changed from Draft to New";
    private final static String NEW_TO_APPROVE = "Ticket Status is changed from New to Approve";
    private final static String DECLINE_TO_DECLINED = "Ticket Status is changed from Decline to Declined";
    private final static String ASSIGNEE_TO_IN_PROGRESS = "Ticket Status is changed Assignee to In Progress";
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
    @Transactional
    public History createTicket(Ticket ticket){
        if (ticket != null){
            History history = new History();
            history.setTicket(ticket);
            history.setDate(Timestamp.from(Instant.now()));
            history.setUserId(userService.getCurrentUser());
            if (ticket.getState().name().equals(DRAFT)) {
                history.setAction(TICKET_CREATED);
                history.setDescription(TICKET_CREATED);
                return historyRepository.save(history);
            }else if (ticket.getState().name().equals(NEW)){
                history.setAction(TICKET_CHANGED);
                history.setDescription(DRAFT_TO_NEW);
                return historyRepository.save(history);
            }else {
                throw new EntityNotFoundException("Ticket not found!");
            }
        }else {
            throw new EntityNotFoundException("Ticket not found!");
        }
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
