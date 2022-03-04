package com.innowise.training.shablinskaya.helpdesk.controller;


import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.service.HistoryService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoryController {

    private HistoryService historyService;
    private TicketService ticketService;
    private TicketDtoConverter ticketDtoConverter;

    @Autowired
    public HistoryController(HistoryService historyService, TicketService ticketService, TicketDtoConverter ticketDtoConverter) {
        this.historyService = historyService;
        this.ticketService = ticketService;
        this.ticketDtoConverter = ticketDtoConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getById(@PathVariable(name = "id") Long id) {
        History history = historyService.getById(id);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping("/history-create/{ticketId}")
    public ResponseEntity<History> createHistory(@PathVariable(name = "ticketId") Long ticketId) {
        Ticket ticket = ticketDtoConverter.toEntity(ticketService.findById(ticketId));

        if (ticket != null) {
            if (ticket.getState().name().compareTo(String.valueOf(State.DRAFT)) == 0
                    || ticket.getState().name().compareTo(String.valueOf(State.NEW)) == 0){
                return ResponseEntity.ok(historyService.create(ticketId));
            }else{
                return null;
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
