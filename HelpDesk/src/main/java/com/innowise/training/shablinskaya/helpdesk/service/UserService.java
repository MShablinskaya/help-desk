package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    List<User> findByName(String name);

    User findByEmail(String email);

    User findById(java.lang.Long id);

    User getCurrentUser();

    boolean hasRole(String... roles);
}
