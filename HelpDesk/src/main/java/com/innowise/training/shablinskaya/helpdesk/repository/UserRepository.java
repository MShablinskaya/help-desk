package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;

import java.util.List;
import java.util.Optional;


public interface UserRepository {

    List<User> findByName(String name);

    List<User> findByRole(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> getById(java.lang.Long id);

    List<User> getAll();

}
