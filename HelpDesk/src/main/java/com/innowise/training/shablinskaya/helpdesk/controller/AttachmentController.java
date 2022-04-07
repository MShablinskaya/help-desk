package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.AttachmentDto;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.AttachmentService;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.responses.ApiResponse;
import io.swagger.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    @PostMapping("/{id}")
    @Operation(summary = "Download file", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<AttachmentDto> uploadFile(@PathVariable(name = "id") Long id,
                                                    @RequestParam("file") MultipartFile file) throws TicketStateException, IOException {
        return ResponseEntity.ok(attachmentService.postFile(id, file));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete attachments", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity deleteFile(@PathVariable(name = "id") Long id) throws TicketStateException {
        attachmentService.deleteFile(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
