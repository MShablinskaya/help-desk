package com.innowise.training.shablinskaya.helpdesk.converter.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.AttachmentConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDtoConverter implements AttachmentConverter {

    @Override
    public AttachmentDto toDto(Attachment attachment) {
        AttachmentDto dto = new AttachmentDto();

        dto.setId(attachment.getId());
        dto.setName(attachment.getName());
        dto.setTicketId(attachment.getTicket().getId());

        return dto;
    }

}
