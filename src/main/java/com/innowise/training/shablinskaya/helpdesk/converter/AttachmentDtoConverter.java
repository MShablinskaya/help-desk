//package com.innowise.training.shablinskaya.helpdesk.converter;
//
//import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
//import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AttachmentDtoConverter {
//
//    public AttachmentDto toDto(Attachment attachment) {
//        AttachmentDto dto = new AttachmentDto();
//
//        dto.setId(attachment.getAttachmentId());
//        dto.setBlob(attachment.getAttachmentBlob());
//        dto.setTicketId(attachment.getTicket());
//        dto.setName(attachment.getName());
//
//        return dto;
//    }
//
//    public Attachment toEntity(AttachmentDto dto) {
//        Attachment attachment = new Attachment();
//
//        attachment.setAttachmentBlob(dto.getBlob());
//        attachment.setTicket(dto.getTicketId());
//        attachment.setName(dto.getName());
//
//        return attachment;
//    }
//}
