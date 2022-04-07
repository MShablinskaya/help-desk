package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.FeedbackDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;

public interface FeedbackConverter {

    FeedbackDto toDto(Feedback feedback);

    Feedback toEntity(FeedbackDto dto);
}
