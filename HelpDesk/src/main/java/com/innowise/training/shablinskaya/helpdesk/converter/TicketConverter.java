package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;

public interface TicketConverter {

    TicketDto toDto(Ticket ticket);

    Ticket toEntity(TicketDto dto);

    Ticket toUpdEntity(TicketDto dto);
}
