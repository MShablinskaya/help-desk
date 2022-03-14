package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

public interface FeedbackService {
    Feedback save(Ticket ticket, FeedbackDto feedbackDto) throws TicketStateException;
}
