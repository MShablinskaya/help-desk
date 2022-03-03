package com.innowise.training.shablinskaya.helpdesk.security.details;

import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.security.JwtUser;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsServiceImpl implements UserDetailsService {
    private UserService userService;

    @Autowired
    public CustomDetailsServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);

        if (user == null){
            throw new UsernameNotFoundException("User is not exist: " + username);
        }
        return JwtUser.fromUserToJwtUser(user);
    }
}
