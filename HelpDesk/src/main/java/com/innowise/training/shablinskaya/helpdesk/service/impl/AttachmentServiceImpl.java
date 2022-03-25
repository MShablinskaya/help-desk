package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.AttachmentDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.AttachmentRepository;
import com.innowise.training.shablinskaya.helpdesk.service.AttachmentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final static int MAX_FILE_SIZE = 5000000;
    private final static String WRONG_SELECTED_TYPE = "The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.";
    private final static String WRONG_SIZE = "The size of attached file should not be greater than 5 Mb. Please select another file.";

    private final AttachmentRepository attachmentRepository;
    private final TicketDtoConverter converter;
    private final AttachmentDtoConverter attachmentDtoConverter;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, TicketDtoConverter converter, AttachmentDtoConverter attachmentDtoConverter) {
        this.attachmentRepository = attachmentRepository;
        this.converter = converter;
        this.attachmentDtoConverter = attachmentDtoConverter;
    }

    @Override
    @Transactional
    public Attachment downloadFile(TicketDto ticket, MultipartFile file) throws IOException, TicketStateException {
        if (ticket.getId() != null) {
            if (file.getSize() <= MAX_FILE_SIZE) {
                String type = FilenameUtils.getExtension(file.getOriginalFilename());
                if (allowedFileTypes(type)) {
                    Attachment attachment = new Attachment();
                    attachment.setName(file.getOriginalFilename());
                    attachment.setTicket(converter.toUpdEntity(ticket));
                    attachment.setAttachment(file.getBytes());

                    return attachmentRepository.save(attachment);
                } else {
                    throw new TicketStateException(WRONG_SELECTED_TYPE);
                }
            } else {
                throw new TicketStateException(WRONG_SIZE);
            }
        } else {
            throw new TicketStateException("Ticket not fount!");
        }

    }


    @Override
    @Transactional
    public void deleteFile(AttachmentDto dto) throws TicketStateException {
        Long id = dto.getId();

        if (id != null) {
            attachmentRepository.remove(attachmentRepository.getById(id).orElseThrow(EntityNotFoundException::new));
        } else {
            throw new TicketStateException("File doesn't exist!");
        }

    }

    @Override
    public AttachmentDto findById(Long id) {
        return attachmentDtoConverter.toDto(attachmentRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }

    private boolean allowedFileTypes(String type) {
        List<String> allowed = new ArrayList<>();

        allowed.add("pdf");
        allowed.add("doc");
        allowed.add("docx");
        allowed.add("png");
        allowed.add("jpeg");
        allowed.add("jpg");

        return allowed.contains(type);
    }
}
