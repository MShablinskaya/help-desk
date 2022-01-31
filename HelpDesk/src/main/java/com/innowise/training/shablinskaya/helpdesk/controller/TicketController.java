package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    private static final Logger log = org.apache.log4j.Logger.getLogger(TicketController.class);

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable(name = "id") Long id){
        TicketDto ticketDto = ticketService.findById(id);

        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @GetMapping("/by-owner/{id}")
    public ResponseEntity<List<TicketDto>> getByOwnerId(@PathVariable(name="id") Long id){
        List<TicketDto> ticketDtos = ticketService.findByOwner(id);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/by-approve/{id}")
    public ResponseEntity<List<TicketDto>> getByApproveId(@PathVariable(name = "id") Long id){
        List<TicketDto> ticketDtos = ticketService.findByApprove(id);

        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }

    @GetMapping("/by-assignee/{id}")
    public  ResponseEntity<List<TicketDto>> getByAssigneeId(@PathVariable(name = "id") Long id){
        List<TicketDto> ticketDtos = ticketService.findByAssignee(id);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/by-state/{state}")
    public ResponseEntity<List<TicketDto>> getByState(@PathVariable(value = "state") State state){
        List<TicketDto> ticketDtos = ticketService.findByState(state);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/by-urgency/{urgency}")
    public ResponseEntity<List<TicketDto>> getByUrgency(@PathVariable(value = "urgency")Urgency urgency){
        List<TicketDto> ticketDtos = ticketService.findByUrgency(urgency);

        return ResponseEntity.ok(ticketDtos);
    }

   // @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER')")
    @PostMapping("/ticket-create")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDto ticketDto){
        Ticket ticket = ticketService.save(ticketDto);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

}
