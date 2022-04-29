package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

public interface FeedbackService {

    Feedback saveFeedback(FeedbackDto feedbackDto, TicketDto ticketDto) throws TicketStateException;

    FeedbackDto postFeedback(Long id, FeedbackDto feedbackDto) throws TicketStateException;

    FeedbackDto getTicketFeedback(Long ticketId) throws TicketStateException;

}
