package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Action;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.TicketRepository;
import com.innowise.training.shablinskaya.helpdesk.service.EmailService;
import com.innowise.training.shablinskaya.helpdesk.service.HistoryService;
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
    private static final String APPROVED = "APPROVED";
    private static final String DECLINED = "DECLINED";
    private static final String CANCELLED = "CANCELLED";
    private static final String IN_PROGRESS = "IN_PROGRESS";
    private static final String DONE = "DONE";
    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String MANAGER = "MANAGER";
    private static final String ENGINEER = "ENGINEER";


    private final TicketRepository ticketRepository;
    private final TicketConverter ticketConverter;
    private final UserService userService;
    private final UserConverter userConverter;
    private final EmailService emailService;
    private final HistoryService historyService;


    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketConverter ticketConverter,
                             UserService userService,
                             UserConverter userConverter,
                             EmailService emailService,
                             HistoryService historyService) {
        this.ticketRepository = ticketRepository;
        this.ticketConverter = ticketConverter;
        this.userService = userService;
        this.userConverter = userConverter;
        this.emailService = emailService;
        this.historyService = historyService;
    }

    @Override
    public TicketDto findById(Long id) {
        return ticketConverter.toDto(ticketRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<TicketDto> findByCurrentUser() {
        User user = userService.getCurrentUser();
        if (user.getRoleId().name().equals(EMPLOYEE) || user.getRoleId().name().equals(MANAGER)) {
            List<TicketDto> ticketDtoList = new ArrayList<>(findByOwner());
            return ticketDtoList.stream().map(ticketDto -> {
                ticketDto.setActions(findAllowedActionsByRole(ticketDto.getId()));
                return ticketDto;
            }).collect(Collectors.toList());
        } else {
            List<TicketDto> ticketDtoList = new ArrayList<>(findByAssignee());
            return ticketDtoList.stream().map(ticketDto -> {
                ticketDto.setActions(findAllowedActionsByRole(ticketDto.getId()));
                return ticketDto;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public List<TicketDto> setActionToTicketDto() throws TicketStateException {
        List<TicketDto> ticketDtoList = new ArrayList<>(findAllTicketsByRole());

        return ticketDtoList.stream().map(ticketDto -> {
            ticketDto.setActions(findAllowedActionsByRole(ticketDto.getId()));
            return ticketDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> findAllTicketsByRole() throws TicketStateException {
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
            if (action.equalsIgnoreCase(Action.DRAFT.name())) {
                ticketDto.setState(DRAFT);
                Ticket ticket = save(ticketDto);

                historyService.recordHistory(ticket);
                TicketDto dto = ticketConverter.toDto(ticket);
                dto.setActions(findAllowedActionsByRole(dto.getId()));
                return dto;
            } else if (action.equalsIgnoreCase(Action.SUBMIT.name())) {
                ticketDto.setState(NEW);
                Ticket ticket = save(ticketDto);

                historyService.recordHistoryForPostedTicket(ticket);
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
        if (currentDate.compareTo(resolutionDate) <= 0) {
            return ticketRepository.create(ticketConverter.toEntity(dto));
        } else {
            throw new EntityNotFoundException("Ticket does not create!");
        }
    }

    @Override
    @Transactional
    public TicketDto editTicket(String action, TicketDto ticketDto) throws TicketStateException {
        if (ticketDto.getId() != null && ticketDto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail())
                && ticketDto.getState().equals(DRAFT) || ticketDto.getState().equals(DECLINED)) {
            if (action.equalsIgnoreCase(Action.SUBMIT.name())) {
                ticketDto = ticketConverter.toDto(changeState(ticketDto.getId(), Action.SUBMIT.name()));
                Ticket ticket = ticketConverter.toUpdEntity(ticketDto);
                historyService.recordHistoryForEditedTicket(ticket);
                return ticketConverter.toDto(ticketRepository.update(ticket));
            } else if (action.equalsIgnoreCase(Action.DRAFT.name())) {
                Ticket ticket = ticketConverter.toUpdEntity(ticketDto);
                historyService.recordHistoryForEditedTicket(ticket);
                return ticketConverter.toDto(ticketRepository.update(ticket));
            } else {
                throw new TicketStateException("Incorrect action!");
            }
        } else {
            throw new TicketStateException("You can't edit this Ticket anymore!");
        }

    }

    @Transactional
    @Override
    public Ticket changeState(Long id, String action) throws TicketStateException {
        User user = userService.getCurrentUser();
        TicketDto dto = ticketConverter.toDto(ticketRepository.getById(id).orElseThrow(EntityNotFoundException::new));
//        char replaceCharacter = '_';
//        action = action.replace(' ', replaceCharacter);
        Action inputAction = Action.valueOf(action.toUpperCase());
        String state = inputAction.getAction();
        if (dto != null && state != null) {
            if (dto.getState().equals(DRAFT) || dto.getState().equals(DECLINED) &&
                    (user.getRoleId().equals(Role.EMPLOYEE) || user.getRoleId().equals(Role.MANAGER))) {
                changeStateFromDraft(dto, State.valueOf(state));
                historyService.recordHistory(ticketConverter.toUpdEntity(dto));
                emailService.sendAllManagerMessage(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));

            } else if (dto.getState().equals(NEW) && user.getRoleId().equals(Role.MANAGER)) {
                changeStateFromNew(dto, State.valueOf(state));
                historyService.recordHistory(ticketConverter.toUpdEntity(dto));
                emailService.sendEmailsForNewTickets(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));

            } else if (dto.getState().equals(APPROVED) && user.getRoleId().equals(Role.ENGINEER)) {
                changeStateFromApprove(dto, State.valueOf(state));
                historyService.recordHistory(ticketConverter.toUpdEntity(dto));
                emailService.sendApproveMessage(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));

            } else if (dto.getState().equals(IN_PROGRESS) && user.getRoleId().equals(Role.ENGINEER)) {
                changeStateFromInProgress(dto, State.valueOf(state));
                historyService.recordHistory(ticketConverter.toUpdEntity(dto));
                emailService.sendCreatorMessage(dto);
                return ticketRepository.update(ticketConverter.toUpdEntity(dto));
            }
        }
        throw new TicketStateException("Something wrong. Chek your request and/or your access rights!");
    }

    @Override
    public List<String> findAllowedActionsByRole(Long id) {
        List<String> actionsList = new ArrayList<>();
        TicketDto dto = findById(id);
        if (dto != null) {
            if (userService.getCurrentUser().getRoleId().name().equals(EMPLOYEE)
                    || userService.getCurrentUser().getRoleId().name().equals(MANAGER)
                    && dto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail())) {
                if (dto.getState().equals(DRAFT) || dto.getState().equals(DECLINED)) {
                    actionsList.add(Action.SUBMIT.name());
                    actionsList.add(Action.EDIT.getAction());
                    actionsList.add(Action.CANCEL.name());

                    return actionsList;
                } else if (dto.getState().equals(DONE)) {
                    actionsList.add(Action.LEAVE_FEEDBACK.getAction());

                    return actionsList;
                } else {
                    actionsList.add("No active actions...");

                    return actionsList;
                }
            } else if (userService.getCurrentUser().getRoleId().name().equals(MANAGER)) {
                if (!dto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail()) && dto.getState().equals(NEW)) {
                    actionsList.add(Action.APPROVE.name());
                    actionsList.add(Action.DECLINE.name());
                    actionsList.add(Action.CANCEL.name());

                } else {
                    actionsList.add("No active actions...");

                }
                return actionsList;
            } else if (userService.getCurrentUser().getRoleId().name().equals(ENGINEER)) {
                if (dto.getState().equals(APPROVED)) {
                    actionsList.add(Action.ASSIGN.name());
                    actionsList.add(Action.CANCEL.name());

                    return actionsList;
                } else if (dto.getState().equals(IN_PROGRESS)
                        && dto.getAssignee().getEmail().equals(userService.getCurrentUser().getEmail())) {
                    actionsList.add(Action.DONE.name());

                    return actionsList;
                } else {
                    actionsList.add("No active actions...");

                    return actionsList;
                }
            }
        } else {
            throw new EntityNotFoundException("Ticket doesn't exist!");
        }

        return actionsList;
    }

    private void changeStateFromDraft(TicketDto dto, State state) throws TicketStateException {
        if (dto.getOwner().getEmail().equals(userService.getCurrentUser().getEmail())) {
            if (!state.name().equalsIgnoreCase(dto.getState())) {
                if (state.name().equalsIgnoreCase(NEW) || state.name().equalsIgnoreCase(CANCELLED)) {
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
                if (state.name().equalsIgnoreCase(APPROVED) || state.name().equalsIgnoreCase(DECLINED) || state.name().equalsIgnoreCase(CANCELLED)) {
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
            if (state.name().equalsIgnoreCase(IN_PROGRESS) || state.name().equalsIgnoreCase(CANCELLED)) {
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
            if (state.name().equalsIgnoreCase(DONE) || state.name().equalsIgnoreCase(CANCELLED)
                    && dto.getAssignee().getEmail().equals(userService.getCurrentUser().getEmail())) {
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
            tickets.forEach(ticket -> ticketDtos.add(ticketConverter.toDto(ticket)));
        }

        return ticketDtos;
    }

    private List<TicketDto> findByApprove() {
        User user = userService.getCurrentUser();
        List<Ticket> tickets = ticketRepository.getByApproveId(user.getId());

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> ticketDtos.add(ticketConverter.toDto(ticket)));
        }
        return ticketDtos;
    }

    private List<TicketDto> findByAssignee() {
        User user = userService.getCurrentUser();
        List<Ticket> tickets = ticketRepository.getByAssigneeId(user.getId());

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> ticketDtos.add(ticketConverter.toDto(ticket)));
        }
        return ticketDtos;
    }

    private List<TicketDto> findByState(State state) {

        List<Ticket> tickets = ticketRepository.getByState(state);

        List<TicketDto> ticketDtos = new ArrayList<>();

        if (tickets != null) {
            tickets.forEach(ticket -> ticketDtos.add(ticketConverter.toDto(ticket)));
        }
        return ticketDtos;
    }

}




