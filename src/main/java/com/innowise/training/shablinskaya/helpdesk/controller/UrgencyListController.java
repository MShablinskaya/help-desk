package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.service.UrgencyListService;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.responses.ApiResponse;
import io.swagger.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/urgency", produces = MediaType.APPLICATION_JSON_VALUE)
public class UrgencyListController {

    private final UrgencyListService urgencyListService;

    @Autowired
    public UrgencyListController(UrgencyListService urgencyListService) {
        this.urgencyListService = urgencyListService;
    }

    @GetMapping
    @Operation(summary = "Get list of all urgency", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<Urgency>> getUrgencyList() {
        return ResponseEntity.ok(urgencyListService.getUrgencyList());
    }
}
