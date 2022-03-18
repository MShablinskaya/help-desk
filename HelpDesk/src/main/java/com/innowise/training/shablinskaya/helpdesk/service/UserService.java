package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;

import java.util.List;

public interface UserService {

    List<User> getAll();

    List<User> findByName(String name);

    List<User> getAllByRole(Role role) throws TicketStateException;

    User findByEmail(String email);

    User findById(Long id);

    User getCurrentUser();

    boolean hasRole(String... roles);

    User refresh(User user);
}
