package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.HistoryService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    private static final Logger log = org.apache.log4j.Logger.getLogger(TicketController.class);
    private final static String DRAFT = "DRAFT";
    private final static String NEW = "NEW";
    private final static String CANCELLED = "CANCELLED";


    private final TicketService ticketService;
    private final UserService userService;
    private final TicketDtoConverter converter;
    private HistoryService historyService;

    @Autowired
    public TicketController(TicketService ticketService, UserService userService, TicketDtoConverter converter, HistoryService historyService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.converter = converter;
        this.historyService = historyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable(name = "id") Long id) {
        TicketDto ticketDto = ticketService.findById(id);

        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @GetMapping("/by-owner/{id}")
    public ResponseEntity<List<TicketDto>> getByOwnerId(@PathVariable(name = "id") Long id) {
        List<TicketDto> ticketDtos = ticketService.findByOwner(id);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/by-approve/{id}")
    public ResponseEntity<List<TicketDto>> getByApproveId(@PathVariable(name = "id") Long id) {
        List<TicketDto> ticketDtos = ticketService.findByApprove(id);

        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }

    @GetMapping("/by-assignee/{id}")
    public ResponseEntity<List<TicketDto>> getByAssigneeId(@PathVariable(name = "id") Long id) {
        List<TicketDto> ticketDtos = ticketService.findByAssignee(id);

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/by-state/{state}")
    public ResponseEntity<List<TicketDto>> getByState(@PathVariable(value = "state") String state) {

        List<TicketDto> ticketDtos = ticketService.findByState(State.valueOf(state.toUpperCase()));

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/by-urgency/{urgency}")
    public ResponseEntity<List<TicketDto>> getByUrgency(@PathVariable(value = "urgency") String urgency) {
        List<TicketDto> ticketDtos = ticketService.findByUrgency(Urgency.valueOf(urgency.toUpperCase()));

        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<TicketDto>> getByCurrentUser() {
        List<TicketDto> ticketDtos = ticketService.findByOwner(userService.getCurrentUser().getId());

        return ResponseEntity.ok(ticketDtos);
    }


    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    @PostMapping("/ticket-create/{action}")
    public ResponseEntity<TicketDto> createTicket(@PathVariable(name = "action") String action, @RequestBody TicketDto ticketDto) throws TicketStateException {
        if (action.equalsIgnoreCase("draft")) {
            ticketDto.setState(DRAFT);
            Ticket ticket = ticketService.save(ticketDto);
            historyService.createTicketHistory(ticket);
            String savedTicketLocation = "tickets/" + ticket.getId();
            return ResponseEntity.created(URI.create(savedTicketLocation)).build();
        } else if (action.equalsIgnoreCase("submit")) {
            ticketDto.setState(NEW);
            Ticket ticket = ticketService.save(ticketDto);
            historyService.createTicketHistory(ticket);
            String savedTicketLocation = "tickets/" + ticket.getId();
            return ResponseEntity.created(URI.create(savedTicketLocation)).build();
        } else if (action.equalsIgnoreCase("cancel")) {
            return new ResponseEntity<>(ticketDto, HttpStatus.NO_CONTENT);
        }else{
            throw new TicketStateException("Unacceptable action!");
        }
    }


    @PutMapping("/change-status/{id}")
    public ResponseEntity<TicketDto> changeTicketState(@PathVariable(name = "id") Long id, @RequestBody State state) throws TicketStateException {
        TicketDto ticketDto = ticketService.findById(id);

        //System.out.println("*************************" + ticketDto.getId());

        if (ticketDto.getId() != null && state != null) {
            ticketService.changeState(ticketDto, state);
            historyService.createTicketHistory(converter.toUpdEntity(ticketDto));

            return new ResponseEntity<>(ticketDto, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Ticket is not exist");
        }

    }


    @PutMapping("/edit-ticket/{action}")
    public ResponseEntity<TicketDto> editTicket(@PathVariable(name = "action") String action, @RequestBody TicketDto ticketDto)
            throws TicketStateException {
        if (ticketDto.getId() != null && ticketDto.getState().equals("DRAFT")) {
            if (action.equalsIgnoreCase("submit")) {
                ticketService.changeState(ticketDto, State.valueOf("NEW"));
                historyService.ticketHistoryForEdit(converter.toUpdEntity(ticketDto));

                return new ResponseEntity<>(ticketDto, HttpStatus.OK);
            } else if (action.equalsIgnoreCase("cancel")) {
                ticketService.changeState(ticketDto, State.valueOf("CANCELLED"));
                historyService.createTicketHistory(converter.toUpdEntity(ticketDto));

                return new ResponseEntity<>(ticketDto, HttpStatus.OK);
            } else {
                throw new TicketStateException("Incorrect action!");
            }
        } else {
            throw new TicketStateException("You can't edit this Ticket anymore!");
        }

    }
}


