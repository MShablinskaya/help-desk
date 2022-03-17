package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.converter.AttachmentDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Attachment;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.AttachmentService;
import com.innowise.training.shablinskaya.helpdesk.service.HistoryService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachmentController {

    private AttachmentService attachmentService;
    private TicketService ticketService;
    private UserService userService;
    private AttachmentDtoConverter converter;
    private HistoryService historyService;


    @Autowired
    public AttachmentController(AttachmentService attachmentService, TicketService ticketService, UserService userService, AttachmentDtoConverter converter, HistoryService historyService) {
        this.attachmentService = attachmentService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.converter = converter;
        this.historyService = historyService;
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PostMapping("/add-attachment/{id}")
    public ResponseEntity<AttachmentDto> uploadFile(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile file) throws TicketStateException, IOException {
        TicketDto ticketDto = ticketService.findById(id);
        User user = userService.getCurrentUser();

        if (ticketDto != null && file != null && ticketDto.getId().equals(user.getId())) {

            Attachment attachment = attachmentService.downloadFile(ticketDto, file);
            historyService.historyForAddAttachment(converter.toDto(attachment));

            return ResponseEntity.ok(converter.toDto(attachment));
        } else {
            throw new TicketStateException("Ticket or file not found!");
        }
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @DeleteMapping("/delete_attachment/{id}")
    public ResponseEntity deleteFile(@PathVariable(name = "id") Long id) throws TicketStateException {
        AttachmentDto dto = attachmentService.findById(id);

        if (dto != null) {
            TicketDto ticketDto = ticketService.findById(dto.getTicketId());
            User user = userService.getCurrentUser();
            if (ticketDto.getOwner().equals(user.getId())) {
                historyService.historyForDeletingAttachment(dto);
                attachmentService.deleteFile(dto);

                return new ResponseEntity(HttpStatus.OK);
            } else {
                throw new TicketStateException("You don't have permission to delete this Attachment");
            }
        } else {
            throw new TicketStateException("Wrong Attachment ID");
        }
    }
}
