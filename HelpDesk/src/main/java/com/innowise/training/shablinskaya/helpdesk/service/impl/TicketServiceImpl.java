package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
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
    private TicketDtoConverter ticketDtoConverter;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketDtoConverter ticketDtoConverter) {
        this.ticketRepository = ticketRepository;
        this.ticketDtoConverter = ticketDtoConverter;
    }

    @Override
    public List<Ticket> getAll() {

        return ticketRepository.getAll();
    }

    @Override
    public TicketDto findById(Long id) {
        return ticketDtoConverter.toDto(ticketRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void save(TicketDto dto) {
        Ticket ticket;
        ticket = ticketDtoConverter.toEntity(dto);

        ticketRepository.create(ticket);
    }

    @Override
    public void update(TicketDto dto) {
        Ticket ticket;
        ticket = ticketDtoConverter.toEntity(dto);

        ticketRepository.update(ticket);
    }

}
