package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private final static String EMAIL = "email";
    private final static String ROLE = Role.EMPLOYEE.name();
    private final static String WRONG_ROLE = Role.EMPLOYEE.name();
    private final static Long ID = 1L;
    @Mock
    private UserService userService;

    @InjectMocks
    private User user;


    private Role role;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        when(userService.getCurrentUser()).thenReturn(user);
        when(userService.findByEmail(EMAIL)).thenReturn(user);
        when(userService.findById(ID)).thenReturn(user);
        when(userService.hasRole(ROLE)).thenReturn(true);
    }


    @Test
    public void getCurrentUserTest() {
        assertEquals(userService.getCurrentUser(), user);

        verify(userService, atLeastOnce()).getCurrentUser();
    }

    @Test
    public void getAllByRoleTest() throws TicketStateException {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userService.getAllByRole(role)).thenReturn(users);

        assertEquals(userService.getAllByRole(role), users);

        verify(userService, atLeastOnce()).getAllByRole(role);
    }

    @Test
    public void findByEmailTest() {
        assertEquals(userService.findByEmail(EMAIL), user);

        verify(userService, atLeastOnce()).findByEmail(EMAIL);
    }

    @Test
    public void findByIdTest() {
        assertEquals(userService.findById(ID), user);

        verify(userService, atLeastOnce()).findById(ID);
    }

    @Test
    public void hasRoleTest() {
        assertTrue(userService.hasRole(ROLE));

        verify(userService, atLeastOnce()).hasRole(ROLE);
    }

}