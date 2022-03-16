package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.AttachmentDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.AttachmentRepository;
import com.innowise.training.shablinskaya.helpdesk.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private AttachmentRepository attachmentRepository;
    private TicketDtoConverter converter;
    private AttachmentDtoConverter attachmentDtoConverter;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, TicketDtoConverter converter, AttachmentDtoConverter attachmentDtoConverter) {
        this.attachmentRepository = attachmentRepository;
        this.converter = converter;
        this.attachmentDtoConverter = attachmentDtoConverter;
    }

    @Override
    @Transactional
    public Attachment downloadFile(TicketDto ticket, MultipartFile file) throws IOException, TicketStateException {
        if (ticket.getId() != null){
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setTicket(converter.toUpdEntity(ticket));
        attachment.setAttachment(file.getBytes());

        return attachmentRepository.save(attachment);}
        else {
            throw new TicketStateException("Ticket not found!");
        }
    }


    @Override
    @Transactional
    public void deleteFile(AttachmentDto dto) throws TicketStateException {
        Long id = dto.getId();

        if (id != null){
            attachmentRepository.remove(attachmentRepository.getById(id).orElseThrow(EntityNotFoundException::new));
        }else {
            throw new TicketStateException("File doesn't exist!");
        }

    }

    @Override
    public AttachmentDto findById(Long id) {
        return attachmentDtoConverter.toDto(attachmentRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }


}
