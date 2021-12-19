package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Feedback;
import com.innowise.training.shablinskaya.helpdesk.repository.FeedbackRepository;

import java.util.List;

public class FeedbackRepositoryImpl implements FeedbackRepository {
    @Override
    public Feedback getById(Integer id) {
        return null;
    }

    @Override
    public List<Feedback> getAllFromTable() {
        return null;
    }

    @Override
    public boolean updateTable(Feedback feedback) {
        return false;
    }

    @Override
    public boolean deleteFromTable(Integer id) {
        return false;
    }

    @Override
    public void addToTable(Feedback feedback) {

    }
}
