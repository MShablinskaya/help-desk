package com.innowise.training.shablinskaya.helpdesk.service;

import com.innowise.training.shablinskaya.helpdesk.dto.UserDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    List<User> findByName(String name);

    UserDto findById(Long id);
}
