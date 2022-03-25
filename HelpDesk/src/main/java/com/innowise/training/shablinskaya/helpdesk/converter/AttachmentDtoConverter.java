package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Component
public class AttachmentDtoConverter {
    private final UserService userService;

    @Autowired
    public AttachmentDtoConverter(UserService userService) {
        this.userService = userService;
    }

    public AttachmentDto toDto(Attachment attachment){
        AttachmentDto dto = new AttachmentDto();

        dto.setId(attachment.getId());
        dto.setName(attachment.getName());
        dto.setTicketId(attachment.getTicket().getId());
        dto.setUserId(userService.getCurrentUser().getId());
        return dto;
    }
}
