package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TicketServiceImpl implements TicketService {
    private static final String DRAFT = "DRAFT";
    private static final String NEW = "NEW";
    private static final String APPROVE = "APPROVED";
    private static final String DECLINE = "DECLINED";
    private static final String CANCEL = "CANCELLED";
    private static final String IN_PROGRESS = "IN_PROGRESS";
    private static final String DONE = "DONE";
    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String MANAGER = "MANAGER";
    private static final String ENGINEER = "ENGINEER";
    private static final String SUBMIT = "Submit";

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
    public List<TicketDto> findByCurrentUser() {
        User user = userService.getCurrentUser();
        if (user.getRoleId().name().equals(EMPLOYEE) || user.getRoleId().name().equals(MANAGER)) {
            return findByOwner();
        } else {
            return findByAssignee();
        }
    }

    @Override
    public List<TicketDto> findByRole() throws TicketStateException {
        User user = userService.getCurrentUser();
        if (user != null) {
            if (user.getRoleId().name().equals(EMPLOYEE)) {

                return findByOwner();
            } else if (user.getRoleId().name().equals(MANAGER)) {
                Set<TicketDto> managersAllTicketsSet = new LinkedHashSet<>(findByOwner());
                managersAllTicketsSet.addAll(findByState(State.NEW));
                List<TicketDto> managersAllTickets = new ArrayList<>(managersAllTicketsSet);
                managersAllTickets.addAll(findByApprove());

                return managersAllTickets;
            } else if (user.getRoleId().name().equals(ENGINEER)) {

                return Stream.of(findByState(State.APPROVED), findByAssignee())
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
            } else {
                throw new TicketStateException("Oops, something going wrong!");
            }
        } else {
            throw new TicketStateException("User not found!");
        }
    }

    @Transactional
    @Override
    public TicketDto postNewTicket(String action, TicketDto ticketDto) throws TicketStateException {
        if (ticketDto != null && action != null) {
            if (action.equalsIgnoreCase(DRAFT)) {
                ticketDto.setState(DRAFT);
                Ticket ticket = save(ticketDto);

                return ticketConverter.toDto(ticket);
            } else if (action.equalsIgnoreCase(SUBMIT)) {
                ticketDto.setState(NEW);
                Ticket ticket = save(ticketDto);

                emailService.sendAllManagerMessage(ticketDto);

                return ticketConverter.toDto(ticket);

            } else {
                throw new TicketStateException("Unacceptable action!");
            }
        } else {
            throw new TicketStateException("Oops! Something going wrong. Chek your request.");
        }
    }

    @Transactional
    @Override
    public Ticket save(TicketDto dto) throws TicketStateException {
        Timestamp now = Timestamp.from(Instant.now());
        LocalDate currentDate = now.toLocalDateTime().toLocalDate();

        Timestamp setTime = dto.getResolutionDate();
        LocalDate resolutionDate = setTime.toLocalDateTime().toLocalDate();
        Ticket ticket = null;
        if (currentDate.compareTo(resolutionDate) <= 0) {
            ticket = ticketRepository.create(ticketConverter.toEntity(dto));
            return ticket;
        } else {
            throw new EntityNotFoundException("Ticket does not create!");
        }
    }

    @Override
    public TicketDto editTicket(String action, TicketDto ticketDto) throws TicketStateException {
        if (ticketDto.getId() != null && ticketDto.getState().equals(DRAFT)
                && ticketDto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail())) {
            if (action.equalsIgnoreCase(SUBMIT)) {
                return ticketConverter.toDto(changeState(ticketDto, State.valueOf(NEW)));
            } else if (action.equalsIgnoreCase(DRAFT)) {
                return ticketDto;
            } else {
                throw new TicketStateException("Incorrect action!");
            }
        } else {
            throw new TicketStateException("You can't edit this Ticket anymore!");
        }

    }

    @Transactional
    @Override
    public TicketDto ticketStatusChange(Long id, State state) throws TicketStateException {
        TicketDto ticketDto = ticketConverter.toDto(ticketRepository.getById(id).orElseThrow(EntityNotFoundException::new));
        return ticketConverter.toDto(changeState(ticketDto, state));
    }


    @Transactional
    @Override
    public Ticket changeState(TicketDto dto, State state) throws TicketStateException {
        User user = userService.getCurrentUser();

        if (dto != null && state != null) {
            if (dto.getState().equals(DRAFT) || dto.getState().equals(DECLINE) &&
                    (user.getRoleId().equals(Role.EMPLOYEE) || user.getRoleId().equals(Role.MANAGER))) {
                changeStateFromDraft(dto, state);
                emailService.sendAllManagerMessage(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));

            } else if (dto.getState().equals(NEW) && user.getRoleId().equals(Role.MANAGER)) {
                changeStateFromNew(dto, state);
                emailService.sendEmailsForNewTickets(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));

            } else if (dto.getState().equals(APPROVE) && user.getRoleId().equals(Role.ENGINEER)) {
                changeStateFromApprove(dto, state);
                emailService.sendApproveMessage(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));

            } else if (dto.getState().equals(IN_PROGRESS) && user.getRoleId().equals(Role.ENGINEER)) {
                changeStateFromInProgress(dto, state);
                emailService.sendCreatorMessage(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));
            }
        }
        throw new TicketStateException("Something wrong. Chek your request and/or your access rights!");
    }


    private void changeStateFromDraft(TicketDto dto, State state) throws TicketStateException {
        if (dto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail())) {
            if (!state.name().equalsIgnoreCase(dto.getState())) {
                if (state.name().equalsIgnoreCase(NEW) || state.name().equalsIgnoreCase(CANCEL)) {
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
                if (state.name().equalsIgnoreCase(APPROVE) || state.name().equalsIgnoreCase(DECLINE) || state.name().equalsIgnoreCase(CANCEL)) {
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
            if (state.name().equalsIgnoreCase(IN_PROGRESS) || state.name().equalsIgnoreCase(CANCEL)) {
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
            if (state.name().equalsIgnoreCase(DONE) || state.name().equalsIgnoreCase(CANCEL)) {
                dto.setState(state.name());
                dto.setAssignee(userConverter.toDto(userService.getCurrentUser()));
            } else {
                throw new TicketStateException("You can't use it for In Progress Ticket!");
            }
        } else {
            throw new TicketStateException("It's nothing to change!");
        }

    }

    private List<TicketDto> findByOwner() {
        User user = userService.getCurrentUser();
        List<Ticket> tickets = ticketRepository.getByOwnerId(user.getId());

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketConverter.toDto(ticket));
            });
        }

        return ticketDtos;
    }

    private List<TicketDto> findByApprove() {
        User user = userService.getCurrentUser();
        List<Ticket> tickets = ticketRepository.getByApproveId(user.getId());

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

    private List<TicketDto> findByAssignee() {
        User user = userService.getCurrentUser();
        List<Ticket> tickets = ticketRepository.getByAssigneeId(user.getId());

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

    private List<TicketDto> findByState(State state) {

        List<Ticket> tickets = ticketRepository.getByState(state);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> {
                ticketDtos.add(ticketConverter.toDto(ticket));
            });
        }
        return ticketDtos;
    }

}




