package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

import java.util.List;

public interface HistoryService {

    void createTicketHistory(Ticket ticket) throws TicketStateException;

    void ticketHistoryForEdit(Ticket ticket) throws TicketStateException;

    void historyForAddAttachment(AttachmentDto dto, Ticket ticket) throws TicketStateException;

    void historyForDeletedAttachment(AttachmentDto dto, Ticket ticket) throws TicketStateException;
}
