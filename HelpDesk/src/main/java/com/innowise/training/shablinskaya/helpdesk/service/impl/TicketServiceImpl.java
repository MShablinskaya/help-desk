package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConvertor;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.repository.TicketRepository;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private TicketDtoConvertor ticketDtoConvertor;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketDtoConvertor ticketDtoConvertor) {
        this.ticketRepository = ticketRepository;
        this.ticketDtoConvertor = ticketDtoConvertor;
    }

    @Override
    public List<Ticket> getAll() {

        return ticketRepository.getAllFromTable();
    }

    @Override
    public TicketDto findById(Long id) {
        return ticketDtoConvertor.toDto(ticketRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void save(TicketDto dto) {
        
    }
}
