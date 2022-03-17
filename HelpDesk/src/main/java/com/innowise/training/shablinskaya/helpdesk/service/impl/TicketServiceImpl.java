package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketDtoConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.repository.TicketRepository;
import com.innowise.training.shablinskaya.helpdesk.service.TicketService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final TicketRepository ticketRepository;
    private final TicketDtoConverter ticketDtoConverter;
    private final UserService userService;
    private static final String DRAFT = "DRAFT";
    private static final String NEW = "NEW";
    private static final String APPROVE = "APPROVED";
    private static final String DECLINE = "DECLINED";
    private static final String CANCEL = "CANCELLED";
    private static final String IN_PROGRESS = "IN_PROGRESS";
    private static final String DONE = "DONE";

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketDtoConverter ticketDtoConverter, UserService userService) {
        this.ticketRepository = ticketRepository;
        this.ticketDtoConverter = ticketDtoConverter;
        this.userService = userService;
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
        Timestamp now = Timestamp.from(Instant.now());
        LocalDate currentDate = now.toLocalDateTime().toLocalDate();

        Timestamp setTime = dto.getResolutionDate();
        LocalDate resolutionDate = setTime.toLocalDateTime().toLocalDate();
        Ticket ticket = null;
        if (currentDate.compareTo(resolutionDate) <= 0) {
            ticket = ticketRepository.create(ticketDtoConverter.toEntity(dto));
        }
        if (ticket == null) {
            throw new EntityNotFoundException("Ticket does not create!");
        }

        return ticket;
    }

    @Transactional
    @Override
    public Ticket changeState(TicketDto dto, State state) throws TicketStateException {
        Long ticketId = dto.getId();

        if (ticketId != null && state != null) {
            dto.setId(ticketId);
            switch (dto.getState()) {
                case DRAFT:
                    changeStateFromDraft(dto, state);
                    return ticketRepository.update(ticketDtoConverter.toUpdEntity(dto));
                case NEW:
                    changeStateFromNew(dto, state);
                    return ticketRepository.update(ticketDtoConverter.toUpdEntity(dto));
                case APPROVE:
                    changeStateFromApprove(dto, state);
                    return ticketRepository.update(ticketDtoConverter.toUpdEntity(dto));
                case IN_PROGRESS:
                    changeStateFromInProgress(dto, state);
                    return ticketRepository.update(ticketDtoConverter.toUpdEntity(dto));
                case DECLINE:
                    changeStateFromeDecline(dto, state);
                    return ticketRepository.update(ticketDtoConverter.toUpdEntity(dto));
                default:
                    throw new TicketStateException("There is no transition status");
            }
    } else

    {
        throw new EntityNotFoundException("Ticket is not exist!");
    }

}


    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    private void changeStateFromDraft(TicketDto dto, State state) throws TicketStateException {
        if (dto.getOwner().equals(userService.getCurrentUser().getId())) {
            if (!state.name().equals(dto.getState())) {
                if (state.name().equals(NEW) || state.name().equals(CANCEL)) {
                    dto.setState(state.name());
                } else {
                    throw new TicketStateException("You can't use it for Draft Ticket!");
                }
            } else {
                throw new TicketStateException("It's nothing to change!");
            }
        }else{
            throw new TicketStateException("You don't own this ticket!");
        }
    }

    @PreAuthorize("@userServiceImpl.hasRole('MANAGER')")
    private void changeStateFromNew(TicketDto dto, State state) throws TicketStateException {
        if (!dto.getOwner().equals(userService.getCurrentUser().getId())) {
            if (!state.name().equals(dto.getState())) {
                if (state.name().equals(APPROVE) || state.name().equals(DECLINE) || state.name().equals(CANCEL)) {
                    dto.setState(state.name());
                    dto.setApprove(userService.getCurrentUser().getId());
                } else {
                    throw new TicketStateException("You can't use it for New Ticket!");
                }
            } else {
                throw new TicketStateException("It's nothing to change!");
            }
        }else{
            throw new TicketStateException("You can't approve your own Ticket");
        }
    }

    @PreAuthorize("@userServiceImpl.hasRole('ENGENEER')")
    private void changeStateFromApprove(TicketDto dto, State state) throws TicketStateException {
        if (!state.name().equals(dto.getState())) {
            if (state.name().equals(IN_PROGRESS) || state.name().equals(CANCEL)) {
                dto.setState(state.name());
                dto.setAssignee(userService.getCurrentUser().getId());
            } else {
                throw new TicketStateException("You can't use it for Approved Ticket!");
            }
        } else {
            throw new TicketStateException("It's nothing to change!");
        }
    }

    @PreAuthorize("@userServiceImpl.hasRole('ENGENEER')")
    private void changeStateFromInProgress(TicketDto dto, State state) throws TicketStateException {
        if (!state.name().equals(dto.getState())) {
            if (state.name().equals(DONE) || state.name().equals(CANCEL)) {
                dto.setState(state.name());
                dto.setAssignee(userService.getCurrentUser().getId());
            } else {
                throw new TicketStateException("You can't use it for In Progress Ticket!");
            }
        } else {
            throw new TicketStateException("It's nothing to change!");
        }

    }

    @PreAuthorize("@userServiceImpl.hasRole('EMPLOYEE', 'MANAGER')")
    private void changeStateFromeDecline(TicketDto dto, State state) throws TicketStateException {
        if (dto.getOwner().equals(userService.getCurrentUser().getId())) {
            if (!state.name().equals(dto.getState())) {
                if (state.name().equals(CANCEL) || state.name().equals(NEW)) {
                    dto.setState(state.name());
                } else {
                    throw new TicketStateException("You can't use it For Done Ticket!");
                }

            } else {
                throw new TicketStateException("It's nothing to change!");
            }
        }else{
            throw new TicketStateException("You don't own this ticket!");
        }
    }
}





