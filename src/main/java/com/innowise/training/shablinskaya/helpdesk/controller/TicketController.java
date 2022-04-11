package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
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

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDto> getById(@PathVariable(name = "ticketId") Long id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<TicketDto>> getByCurrentUser() {
        return ResponseEntity.ok(ticketService.findByCurrentUser());
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() throws TicketStateException {
        return ResponseEntity.ok(ticketService.findByRole());
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PostMapping("/{action}")
    public ResponseEntity<TicketDto> postNewTicket(@PathVariable(name = "action") String action,
                                                   @RequestBody TicketDto ticketDto) throws TicketStateException {
        return ResponseEntity.ok(ticketService.postNewTicket(action, ticketDto));
    }

    @PutMapping("/{action}")
    public ResponseEntity<TicketDto> changeTicketState(@PathVariable(name = "action") State state,
                                                       @RequestBody Long id) throws TicketStateException {
        return ResponseEntity.ok(ticketService.ticketStatusChange(id, state));
    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PutMapping("/edit/{action}")
    public ResponseEntity<TicketDto> editTicket(@PathVariable(name = "action") String action,
                                                @RequestBody TicketDto ticketDto) throws TicketStateException {
        return ResponseEntity.ok(ticketService.editTicket(action, ticketDto));
    }
}


