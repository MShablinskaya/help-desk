package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.dto.UserDto;
import com.innowise.training.shablinskaya.helpdesk.converter.UserDtoConvertor;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.repository.UserRepository;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDtoConvertor userDtoConvertor;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDtoConvertor userDtoConvertor) {
        this.userRepository = userRepository;
        this.userDtoConvertor = userDtoConvertor;
    }

    @Override
    public List<User> getAll() {

        return userRepository.getAllFromTable();
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserDto findById(Long id) {

        return userDtoConvertor.toDto(userRepository.getById(id).orElseThrow(EntityNotFoundException::new));
    }
}

