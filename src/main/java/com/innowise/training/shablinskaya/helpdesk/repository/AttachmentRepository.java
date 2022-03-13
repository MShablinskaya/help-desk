package com.innowise.training.shablinskaya.helpdesk.repository;


import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    Optional<Attachment> getById(Long id);

    List<Attachment> getByTicketId(Long id);

    Attachment update(Attachment attachment);

    void save(Attachment entity);
}
