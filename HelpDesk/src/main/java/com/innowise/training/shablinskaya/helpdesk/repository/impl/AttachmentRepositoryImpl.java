package com.innowise.training.shablinskaya.helpdesk.repository.impl;

import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.repository.AttachmentRepository;

import java.util.List;

public class AttachmentRepositoryImpl implements AttachmentRepository {
    @Override
    public Attachment getById(Integer id) {
        return null;
    }

    @Override
    public List<Attachment> getAllFromTable() {
        return null;
    }

    @Override
    public boolean updateTable(Attachment attachment) {
        return false;
    }

    @Override
    public boolean deleteFromTable(Integer id) {
        return false;
    }

    @Override
    public void addToTable(Attachment attachment) {

    }
}
