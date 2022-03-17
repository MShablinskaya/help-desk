package com.innowise.training.shablinskaya.helpdesk.repository;


import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;

import java.util.Optional;

public interface FeedbackRepository {

    Optional<Feedback> getById(Long id);

    Feedback save(Feedback feedback);

}
