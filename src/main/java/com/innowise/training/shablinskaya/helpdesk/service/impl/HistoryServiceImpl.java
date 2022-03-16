package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
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
    private final static String DRAFT_TO_CANCELLED = "Ticket was Cancelled";
    private final static String NEW_TO_APPROVE = "Ticket Status is changed from New to Approve";
    private final static String NEW_TO_DECLINED = "Ticket Status is changed from New to Declined";
    private final static String APPROVE_TO_IN_PROGRESS = "Ticket Status is changed Assignee to In Progress";
    private final static String I_PROGRESS_TO_DONE = "Ticket Status is changed In Progress to Done";
    private final static String NEW = "NEW";
    private final static String DRAFT = "DRAFT";
    private final static String APPROVE = "APPROVED";
    private final static String DECLINE = "DECLINED";
    private final static String CANCEL = "CANCELLED";
    private final static String IN_PROGRESS = "IN_PROGRESS";
    private final static String DONE = "DONE";
    private final static String EDIT = "Ticket is edited";

    private HistoryRepository historyRepository;
    private UserService userService;
    private TicketService ticketService;


    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, UserService userService, TicketService ticketService) {
        this.historyRepository = historyRepository;
        this.userService = userService;
        this.ticketService = ticketService;
    }


    @Override
    @Transactional
    public History createTicketHistory(Ticket ticket) throws TicketStateException {
        if (ticket != null) {
            History history = new History();
            history.setTicket(ticket);
            history.setDate(Timestamp.from(Instant.now()));
            history.setUserId(userService.getCurrentUser());
            switch (ticket.getState().name()) {
                case DRAFT:
                    creation(history);
                    return historyRepository.save(history);
                case NEW:
                    historyForNew(history);
                    return historyRepository.save(history);
                case CANCEL:
                    historyForCancelled(history);
                    return historyRepository.save(history);

                case APPROVE:
                    historyForApprove(history);
                    return historyRepository.save(history);

                case DECLINE:
                    historyForDecline(history);
                    return historyRepository.save(history);

                case IN_PROGRESS:
                    historyForInProgress(history);
                    return historyRepository.save(history);

                case DONE:
                    historyForDone(history);
                    return historyRepository.save(history);

                default:
                    throw new TicketStateException("Incorrect transition!");
            }
        } else {
            throw new EntityNotFoundException("Ticket not found!");
        }
    }

    @Override
    @Transactional
    public History ticketHistoryForEdit(Ticket ticket) throws TicketStateException {
        if (ticket != null) {
            History history = new History();
            history.setTicket(ticket);
            history.setDate(Timestamp.from(Instant.now()));
            history.setUserId(userService.getCurrentUser());
            if (ticket.getState().name().equals(NEW)){
                edit(history);
                return historyRepository.save(history);
            }else{
                throw new TicketStateException("Incorrect transition!");
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

    private void creation(History history) {
        history.setAction(TICKET_CREATED);
        history.setDescription(TICKET_CREATED);
        historyRepository.save(history);
    }

    private void historyForNew(History history) {
        history.setAction(TICKET_CHANGED);
        history.setDescription(DRAFT_TO_NEW);
        historyRepository.save(history);
    }

    private void historyForApprove(History history) {
        history.setAction(TICKET_CHANGED);
        history.setDescription(NEW_TO_APPROVE);
        historyRepository.save(history);
    }

    private void historyForDecline(History history) {
        history.setAction(TICKET_CHANGED);
        history.setDescription(NEW_TO_DECLINED);
        historyRepository.save(history);
    }

    private void historyForCancelled(History history) {
        history.setAction(TICKET_CHANGED);
        history.setDescription(DRAFT_TO_CANCELLED);
        historyRepository.save(history);
    }

    private void historyForInProgress(History history){
        history.setAction(TICKET_CHANGED);
        history.setDescription(APPROVE_TO_IN_PROGRESS);
        historyRepository.save(history);
    }

    private void historyForDone(History history){
        history.setAction(TICKET_CHANGED);
        history.setDescription(I_PROGRESS_TO_DONE);
        historyRepository.save(history);
    }

    private void edit(History history){
        history.setAction(EDIT);
        history.setDescription(EDIT);
        historyRepository.save(history);
    }
}
