package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    List<User> findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
