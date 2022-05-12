package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    @PostMapping("/{id}")
    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<List<AttachmentDto>> uploadFile(@PathVariable(name = "id") Long id,
                                                          @RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(attachmentService.multipleUploadFile(id, files));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity deleteFile(@PathVariable(name = "id") Long id) throws TicketStateException {
        attachmentService.deleteFile(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<AttachmentDto>> getAttachmentByTicketId(@PathVariable(name = "ticketId") Long ticketId) throws TicketStateException {

        return ResponseEntity.ok(attachmentService.getAttachmentsByTicketId(ticketId));
    }
}
