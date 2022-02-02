package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.repository.TicketRepository;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private TicketDtoConverter ticketDtoConverter;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketDtoConverter ticketDtoConverter) {
        this.ticketRepository = ticketRepository;
        this.ticketDtoConverter = ticketDtoConverter;
    }

//    @Override
//    public List<TicketDto> getAll() {
//        List<Ticket> tickets = ticketRepository.getAll();
//
//        List<TicketDto> ticketDtos = new ArrayList<>();
//
//        if (tickets != null) {
//            tickets.forEach(ticket -> {
//                ticketDtos.add(ticketDtoConverter.toDto(ticket));
//            });
//        }
//
//        return ticketDtos;
//    }

    @Override
    public TicketDto findById(Long id) {
        return ticketDtoConverter.toDto(ticketRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<TicketDto> findByOwner(Long id) {
        List<Ticket> tickets = ticketRepository.getByOwnerId(id);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketDtoConverter.toDto(ticket));
            });
        }

        return ticketDtos;
    }

    @Override
    public List<TicketDto> findByApprove(Long id) {
        List<Ticket> tickets = ticketRepository.getByApproveId(id);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketDtoConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

    @Override
    public List<TicketDto> findByAssignee(Long id) {
        List<Ticket> tickets = ticketRepository.getByAssigneeId(id);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketDtoConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

    @Override
    public List<TicketDto> findByState(State state) {

        List<Ticket> tickets = ticketRepository.getByState(state);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketDtoConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

    @Override
    public List<TicketDto> findByUrgency(Urgency urgency) {
        List<Ticket> tickets = ticketRepository.getByUrgency(urgency);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketDtoConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

    @Transactional
    @Override
    public Ticket save(TicketDto dto) {
        Ticket ticket;
        ticket = ticketDtoConverter.toEntity(dto);

        ticketRepository.create(ticket);
        return ticket;
    }

    @Transactional
    @Override
    public void update(TicketDto dto) {
        Ticket ticket;
        ticket = ticketDtoConverter.toEntity(dto);

        ticketRepository.update(ticket);
    }

}
