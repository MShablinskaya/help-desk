package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;

public interface AttachmentConverter {
    AttachmentDto toDto(Attachment attachment);
}
