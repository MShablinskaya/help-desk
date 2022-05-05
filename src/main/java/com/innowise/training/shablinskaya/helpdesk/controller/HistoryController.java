package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.HistoryDto;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<HistoryDto>> getAllTicketHistory(@PathVariable(name = "ticketId") Long ticketId) throws TicketStateException{
        return ResponseEntity.ok(historyService.getTicketHistory(ticketId));
    }
}
