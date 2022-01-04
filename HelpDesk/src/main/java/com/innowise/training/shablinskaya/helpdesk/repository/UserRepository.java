package com.innowise.training.shablinskaya.helpdesk.repository;

import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends TableManagerRepository<User, Integer> {

    List<User> findByName(String name);

    List<User> findByRole(Role role);

    Optional<User> findByEmail(String email);
}
