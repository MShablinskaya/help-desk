package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.AuthRequestDto;
import com.innowise.training.shablinskaya.helpdesk.dto.AuthResponseDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.security.JwtProvider;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(value = "/")
public class LoginController {

    private static final Logger log = org.apache.log4j.Logger.getLogger(LoginController.class);

    private UserService userService;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public LoginController() {
    }

    @Autowired
    public LoginController(UserService userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponseDto authResponseDto(@RequestBody AuthRequestDto requestDto) throws AuthenticationException {
        User user = userService.findByEmail(requestDto.getEmail());

        if (user != null) {
            if (passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(requestDto.getPassword()))) {
                String token = jwtProvider.generateToken(user.getEmail());
                return new AuthResponseDto(token, user.getLastName(), user.getRoleId().toString());
            } else {
                throw new AuthenticationException("Invalid Password");
            }
        } else {
            throw new AuthenticationException("User not found!");
        }
    }
}
