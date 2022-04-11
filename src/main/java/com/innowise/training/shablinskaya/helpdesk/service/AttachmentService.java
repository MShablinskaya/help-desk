package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

public interface AttachmentService {
    Attachment uploadFile(TicketDto ticket, MultipartFile file) throws IOException, TicketStateException;

    void removeFile(AttachmentDto dto) throws TicketStateException;

    void deleteFile(Long id) throws TicketStateException;

    AttachmentDto multipleUploadFile(Long id, MultipartFile file) throws TicketStateException, IOException;
}
