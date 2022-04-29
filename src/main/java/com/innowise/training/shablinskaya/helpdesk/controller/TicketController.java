package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.responses.ApiResponse;
import io.swagger.oas.annotations.security.SecurityRequirement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    private static final Logger log = org.apache.log4j.Logger.getLogger(TicketController.class);

    private final TicketService ticketService;
    private final TicketConverter converter;

    @Autowired
    public TicketController(TicketService ticketService, TicketConverter converter) {
        this.ticketService = ticketService;
        this.converter = converter;
    }

    @GetMapping("/{ticketId}")
    @Operation(summary = "Get ticket by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<TicketDto> getById(@PathVariable(name = "ticketId") Long id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @GetMapping("/my-tickets")
    @Operation(summary = "Get tickets current user owns", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<TicketDto>> getByCurrentUser() {
        return ResponseEntity.ok(ticketService.findByCurrentUser());
    }

    @GetMapping
    @Operation(summary = "Get all tickets are available to current user.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<TicketDto>> getAllTickets() throws TicketStateException {
        return ResponseEntity.ok(ticketService.setActionToTicketDto());
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PostMapping("/create/{action}")
    @Operation(summary = "Post new ticket", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<TicketDto> postNewTicket(@PathVariable(name = "action") String action,
                                                   @RequestBody TicketDto ticketDto) throws TicketStateException {
        return ResponseEntity.ok(ticketService.postNewTicket(action, ticketDto));
    }

    @PutMapping("/{action}")
    @Operation(summary = "Update tickets when their status changed.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<TicketDto> changeTicketState(@PathVariable(name = "action") String state,
                                                       @RequestBody Long id) throws TicketStateException {
        return ResponseEntity.ok(converter.toDto(ticketService.changeState(id, state)));
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PutMapping("/edit/{action}")
    @Operation(summary = "Edit draft ticket.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<TicketDto> editTicket(@PathVariable(name = "action") String action,
                                                @RequestBody TicketDto ticketDto) throws TicketStateException {
        return ResponseEntity.ok(ticketService.editTicket(action, ticketDto));
    }

    @GetMapping("/actions/{id}")
    @Operation(summary = "Get allowed actions ith tickets in dependence of user role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<String>> getAllowedActions(@PathVariable(name = "id") Long id){

        return ResponseEntity.ok(ticketService.findAllowedActionsByRole(id));
    }
}


