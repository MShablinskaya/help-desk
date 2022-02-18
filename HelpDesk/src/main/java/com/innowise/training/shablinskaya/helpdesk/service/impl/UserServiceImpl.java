package com.innowise.training.shablinskaya.helpdesk.service.impl;


import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.repository.UserRepository;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = org.apache.log4j.Logger.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findById(java.lang.Long id) {
        return userRepository.getById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                EntityNotFoundException::new);
    }

    @Override
    public boolean hasRole(String... roles) {
        User user = getCurrentUser();
        for(String role : roles){
            if(role.equals(user.getRoleId().toString())){
                return true;
            }
        }
        return false;
    }

    @Override
    public User refresh(User user) {
        return userRepository.update(user);
    }

}

