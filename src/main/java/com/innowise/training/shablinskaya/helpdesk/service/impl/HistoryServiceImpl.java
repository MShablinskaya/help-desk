package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.impl.TicketConverterImpl;
import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.HistoryRepository;
import com.innowise.training.shablinskaya.helpdesk.service.HistoryService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class HistoryServiceImpl implements HistoryService {
    private static final String TICKET_CREATED = "Ticket is created";
    private static final String TICKET_CHANGED = "Ticket Status is changed";
    private static final String DRAFT_TO_NEW = "Ticket Status is changed from Draft to New";
    private static final String DRAFT_TO_CANCELLED = "Ticket was Cancelled";
    private static final String NEW_TO_APPROVE = "Ticket Status is changed from New to Approve";
    private static final String NEW_TO_DECLINED = "Ticket Status is changed from New to Declined";
    private static final String APPROVE_TO_IN_PROGRESS = "Ticket Status is changed Assignee to In Progress";
    private static final String I_PROGRESS_TO_DONE = "Ticket Status is changed In Progress to Done";
    private static final String NEW = "NEW";
    private static final String DRAFT = "DRAFT";
    private static final String APPROVE = "APPROVED";
    private static final String DECLINE = "DECLINED";
    private static final String CANCEL = "CANCELLED";
    private static final String IN_PROGRESS = "IN_PROGRESS";
    private static final String DONE = "DONE";
    private static final String EDIT = "Ticket is edited";
    private static final String ADD = "File is attached ";
    private static final String DELETE = "File is removed ";

    private final HistoryRepository historyRepository;
    private final UserService userService;



    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, UserService userService) {
        this.historyRepository = historyRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void createTicketHistory(Ticket ticket) throws TicketStateException {
        if (ticket != null) {
            History history = new History();
            history.setTicket(ticket);
            history.setDate(Timestamp.from(Instant.now()));
            history.setUserId(userService.getCurrentUser());
            switch (ticket.getState().name()) {
                case DRAFT:
                    creation(history);
                    historyRepository.save(history);
                    break;

                case NEW:
                    historyForNew(history);
                    historyRepository.save(history);
                    break;

                case CANCEL:
                    historyForCancelled(history);
                    historyRepository.save(history);
                    break;

                case APPROVE:
                    historyForApprove(history);
                    historyRepository.save(history);
                    break;

                case DECLINE:
                    historyForDecline(history);
                    historyRepository.save(history);
                    break;

                case IN_PROGRESS:
                    historyForInProgress(history);
                    historyRepository.save(history);
                    break;

                case DONE:
                    historyForDone(history);
                    historyRepository.save(history);
                    break;

                default:
                    throw new TicketStateException("Incorrect transition!");
            }
        } else {
            throw new EntityNotFoundException("Ticket not found!");
        }
    }

    @Override
    @Transactional
    public void ticketHistoryForEdit(Ticket ticket) throws TicketStateException {
        if (ticket != null) {
            History history = new History();
            history.setTicket(ticket);
            history.setDate(Timestamp.from(Instant.now()));
            history.setUserId(userService.getCurrentUser());
            if (ticket.getState().name().equals(NEW)) {
                edit(history);
                historyRepository.save(history);
            } else {
                throw new TicketStateException("Incorrect transition!");
            }
        } else {
            throw new EntityNotFoundException("Ticket not found!");
        }
    }

    @Override
    @Transactional
    public void historyForAddAttachment(AttachmentDto dto, Ticket ticket) throws TicketStateException {
        createHistory(dto, ADD, ticket);
    }

    @Override
    @Transactional
    public void historyForDeletedAttachment(AttachmentDto dto, Ticket ticket) throws TicketStateException {
        createHistory(dto, DELETE, ticket);
    }

    private void createHistory(AttachmentDto dto, String action, Ticket ticket) throws TicketStateException {
        if (dto != null) {
            History history = new History();
            history.setTicket(ticket);
            history.setDate(Timestamp.from(Instant.now()));
            history.setUserId(userService.getCurrentUser());
            history.setAction(action);
            history.setDescription(action + dto.getName());
            historyRepository.save(history);
        } else {
            throw new TicketStateException("File doesn't exist!");
        }
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

    private void historyForInProgress(History history) {
        history.setAction(TICKET_CHANGED);
        history.setDescription(APPROVE_TO_IN_PROGRESS);
        historyRepository.save(history);
    }

    private void historyForDone(History history) {
        history.setAction(TICKET_CHANGED);
        history.setDescription(I_PROGRESS_TO_DONE);
        historyRepository.save(history);
    }

    private void edit(History history) {
        history.setAction(EDIT);
        history.setDescription(EDIT);
        historyRepository.save(history);
    }
}
