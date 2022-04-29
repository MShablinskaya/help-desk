package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.HistoryDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

import java.util.List;

public interface HistoryService {

    void recordHistory(Ticket ticket) throws TicketStateException;

    void recordHistoryForPostedTicket(Ticket ticket) throws TicketStateException;

    void recordHistoryForEditedTicket(Ticket ticket) throws TicketStateException;

    void recordHistoryForUploadedAttachment(AttachmentDto dto, Ticket ticket) throws TicketStateException;

    void recordHistoryForDeletedAttachment(AttachmentDto dto, Ticket ticket) throws TicketStateException;

    List<HistoryDto> getTicketHistory(Long ticketId) throws TicketStateException;
}
