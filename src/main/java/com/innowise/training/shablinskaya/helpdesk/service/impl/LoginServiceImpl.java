package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.dto.AuthRequestDto;
import com.innowise.training.shablinskaya.helpdesk.dto.AuthResponseDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.security.JwtProvider;
import com.innowise.training.shablinskaya.helpdesk.service.LoginService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginServiceImpl(UserService userService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public AuthResponseDto login(AuthRequestDto requestDto) {
        String email = requestDto.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        User user = userService.findByEmail(email);

        if (user != null) {

            return userToken(email, user, jwtProvider);
        }

        throw new BadCredentialsException("User not found!");
    }

    private static AuthResponseDto userToken(String email, User user, JwtProvider jwtProvider) {
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRoleId());

        String token = jwtProvider.createToken(email, roles);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setEmail(email);
        authResponseDto.setRole(roles);
        authResponseDto.setToken(token);

        return authResponseDto;
    }
}
