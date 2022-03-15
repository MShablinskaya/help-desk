package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.FeedBackDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.FeedbackRepository;
import com.innowise.training.shablinskaya.helpdesk.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackRepository feedbackRepository;
    private FeedBackDtoConverter converter;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedBackDtoConverter converter) {
        this.feedbackRepository = feedbackRepository;
        this.converter = converter;
    }

    @Transactional
    @Override
    public Feedback save(FeedbackDto dto) throws TicketStateException {

        if (dto.getRate() != null){
            return feedbackRepository.save(converter.toEntity(dto));
        }else{
            throw new TicketStateException("You can't leave feedback without rate!");
        }

    }
}
