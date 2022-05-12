package com.innowise.training.shablinskaya.helpdesk.repository;


import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    Optional<Attachment> getById(Long id);

    void remove(Attachment attachment);

    Attachment save(Attachment attachment);

    List<Attachment> getByTicketId(Long ticketId);
}
