package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.converter.TicketConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketConverterTest {

    @Mock
    private TicketConverter converter;

    @InjectMocks
    private Ticket ticket;

    @InjectMocks
    private TicketDto dto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(converter.toDto(ticket)).thenReturn(dto);
        when(converter.toEntity(dto)).thenReturn(ticket);
        when(converter.toUpdEntity(dto)).thenReturn(ticket);
    }

    @Test
    public void toDtoTest() {
        assertEquals(converter.toDto(ticket), dto);

        verify(converter, atLeastOnce()).toDto(ticket);
    }

    @Test
    public void toEntityTest() {
        assertEquals(converter.toEntity(dto), ticket);

        verify(converter, atLeastOnce()).toEntity(dto);
    }

    @Test
    public void toUpdEntityTest() {
        assertEquals(converter.toUpdEntity(dto), ticket);

        verify(converter, atLeastOnce()).toUpdEntity(dto);

    }
}