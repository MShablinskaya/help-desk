package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.TicketRepository;
import com.innowise.training.shablinskaya.helpdesk.service.EmailService;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private static final String DRAFT = "DRAFT";
    private static final String NEW = "NEW";
    private static final String APPROVE = "APPROVED";
    private static final String DECLINE = "DECLINED";
    private static final String CANCEL = "CANCELLED";
    private static final String IN_PROGRESS = "IN_PROGRESS";
    private static final String DONE = "DONE";

    private final TicketRepository ticketRepository;
    private final TicketConverter ticketConverter;
    private final UserService userService;
    private final UserConverter userConverter;
    private final EmailService emailService;


    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketConverter ticketConverter, UserService userService, UserConverter userConverter, EmailService emailService) {
        this.ticketRepository = ticketRepository;
        this.ticketConverter = ticketConverter;
        this.userService = userService;
        this.userConverter = userConverter;
        this.emailService = emailService;
    }

    @Override
    public TicketDto findById(Long id) {
        return ticketConverter.toDto(ticketRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<TicketDto> findByOwner(Long id) {
        List<Ticket> tickets = ticketRepository.getByOwnerId(id);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketConverter.toDto(ticket));
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
                ticketDtos.add(ticketConverter.toDto(ticket));
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
                ticketDtos.add(ticketConverter.toDto(ticket));
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
                ticketDtos.add(ticketConverter.toDto(ticket));
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
                ticketDtos.add(ticketConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

    @Transactional
    @Override
    public Ticket save(TicketDto dto) {
        Timestamp now = Timestamp.from(Instant.now());
        LocalDate currentDate = now.toLocalDateTime().toLocalDate();

        Timestamp setTime = dto.getResolutionDate();
        LocalDate resolutionDate = setTime.toLocalDateTime().toLocalDate();
        Ticket ticket = null;
        if (currentDate.compareTo(resolutionDate) <= 0) {
            ticket = ticketRepository.create(ticketConverter.toEntity(dto));
        }
        if (ticket == null) {
            throw new EntityNotFoundException("Ticket does not create!");
        }

        return ticket;
    }

    @Transactional
    @Override
    public Ticket changeState(TicketDto dto, State state) throws TicketStateException {
        User user = userService.getCurrentUser();

        if (dto != null && state != null) {
            if (user.getRoleId().equals(Role.EMPLOYEE) || user.getRoleId().equals(Role.MANAGER)) {
                if (dto.getState().equals(DRAFT) || dto.getState().equals(DECLINE)) {

                    changeStateFromDraft(dto, state);
                    emailService.sendAllManagerMessage(dto);

                    return ticketRepository.update(ticketConverter.toUpdEntity(dto));
                }
            }
        }
        throw new TicketStateException("You don't own this ticket!");
    }


    private void changeStateFromDraft(TicketDto dto, State state) throws TicketStateException {
        if (dto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail())) {
            if (!state.name().equals(dto.getState())) {
                if (state.name().equals(NEW) || state.name().equals(CANCEL)) {
                    dto.setState(state.name());
                } else {
                    throw new TicketStateException("You can't use it for Draft Ticket!");
                }
            } else {
                throw new TicketStateException("It's nothing to change!");
            }
        } else {
            throw new TicketStateException("You don't own this ticket!");
        }
    }


    private void changeStateFromNew(TicketDto dto, State state) throws TicketStateException {
        if (!dto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail())) {
            if (!state.name().equals(dto.getState())) {
                if (state.name().equals(APPROVE) || state.name().equals(DECLINE) || state.name().equals(CANCEL)) {
                    dto.setState(state.name());
                    dto.setApprove(userConverter.toDto(userService.getCurrentUser()));
                } else {
                    throw new TicketStateException("You can't use it for New Ticket!");
                }
            } else {
                throw new TicketStateException("It's nothing to change!");
            }
        } else {
            throw new TicketStateException("You can't approve your own Ticket");
        }
    }


    private void changeStateFromApprove(TicketDto dto, State state) throws TicketStateException {
        if (!state.name().equals(dto.getState())) {
            if (state.name().equals(IN_PROGRESS) || state.name().equals(CANCEL)) {
                dto.setState(state.name());
                dto.setAssignee(userConverter.toDto(userService.getCurrentUser()));
            } else {
                throw new TicketStateException("You can't use it for Approved Ticket!");
            }
        } else {
            throw new TicketStateException("It's nothing to change!");
        }
    }


    private void changeStateFromInProgress(TicketDto dto, State state) throws TicketStateException {
        if (!state.name().equals(dto.getState())) {
            if (state.name().equals(DONE) || state.name().equals(CANCEL)) {
                dto.setState(state.name());
                dto.setAssignee(userConverter.toDto(userService.getCurrentUser()));
            } else {
                throw new TicketStateException("You can't use it for In Progress Ticket!");
            }
        } else {
            throw new TicketStateException("It's nothing to change!");
        }

    }

}




