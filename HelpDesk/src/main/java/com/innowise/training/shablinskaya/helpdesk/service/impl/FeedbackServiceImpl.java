package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.impl.FeedBackConverterImpl;
import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.FeedbackRepository;
import com.innowise.training.shablinskaya.helpdesk.service.EmailService;
import com.innowise.training.shablinskaya.helpdesk.service.FeedbackService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedBackConverterImpl converter;
    private final EmailService emailService;
    private final TicketService ticketService;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedBackConverterImpl converter, EmailService emailService, TicketService ticketService) {
        this.feedbackRepository = feedbackRepository;
        this.converter = converter;
        this.emailService = emailService;
        this.ticketService = ticketService;
    }

    @Transactional
    @Override
    public Feedback save(FeedbackDto dto) throws TicketStateException {

        if (dto.getRate() != null){
            emailService.sendAssigneeMessage(ticketService.findById(dto.getTicketId()));
            return feedbackRepository.save(converter.toEntity(dto));
        }else{
            throw new TicketStateException("You can't leave feedback without rate!");
        }

    }
}
