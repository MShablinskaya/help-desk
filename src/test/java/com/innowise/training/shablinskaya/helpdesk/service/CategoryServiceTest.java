package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.print.attribute.standard.MediaSize;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    private final static Long ID = 1L;
    private final static String NAME = "name";

    @Mock
    private CategoryService service;

    @InjectMocks
    private Category category;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(service.findById(ID)).thenReturn(category);
        when(service.findByName(NAME)).thenReturn(category);
    }

    @Test
    public void findById() {
        assertEquals(service.findById(ID), category);

        verify(service, atLeastOnce()).findById(ID);
    }

    @Test
    public void findByName() {
        assertEquals(service.findByName(NAME), category);

        verify(service, atLeastOnce()).findByName(NAME);
    }
}