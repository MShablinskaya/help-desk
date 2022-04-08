package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.impl.FeedbackConverterImpl;
import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.FeedbackRepository;
import com.innowise.training.shablinskaya.helpdesk.service.EmailService;
import com.innowise.training.shablinskaya.helpdesk.service.FeedbackService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final static String DONE = "DONE";
    private final FeedbackRepository feedbackRepository;
    private final FeedbackConverterImpl converter;
    private final EmailService emailService;
    private final TicketService ticketService;
    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackConverterImpl converter, EmailService emailService, TicketService ticketService, UserService userService, UserConverter userConverter) {
        this.feedbackRepository = feedbackRepository;
        this.converter = converter;
        this.emailService = emailService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Transactional
    @Override
    public FeedbackDto postFeedback(Long id, FeedbackDto feedbackDto) throws TicketStateException {
        TicketDto ticketDto = ticketService.findById(id);
        return converter.toDto(saveFeedback(feedbackDto, ticketDto));
    }

    @Transactional
    @Override
    public Feedback saveFeedback(FeedbackDto dto, TicketDto ticketDto) throws TicketStateException {
        if (ticketDto != null
                && ticketDto.getState().equals(DONE)
                && ticketDto.getOwner().equals(userConverter.toDto(userService.getCurrentUser()))) {
            if (dto.getRate() != null) {
                dto.setTicketId(ticketDto.getId());
                emailService.sendAssigneeMessage(ticketService.findById(dto.getTicketId()));
                return feedbackRepository.save(converter.toEntity(dto));
            } else {
                throw new TicketStateException("You can't leave feedback without rate!");
            }
        } else {
            throw new TicketStateException("Something went wrong.");
        }
    }

}
