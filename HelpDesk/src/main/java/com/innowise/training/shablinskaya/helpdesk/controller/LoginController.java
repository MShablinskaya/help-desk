package com.innowise.training.shablinskaya.helpdesk.controller;

import com.innowise.training.shablinskaya.helpdesk.dto.AuthRequestDto;
import com.innowise.training.shablinskaya.helpdesk.dto.AuthResponseDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.security.JwtProvider;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class LoginController {

    private static final Logger log = org.apache.log4j.Logger.getLogger(LoginController.class);

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public LoginController() {
    }

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequestDto requestDto) {
        String email = requestDto.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));

        User user = userService.findByEmail(email);

        if (user != null) {
           {
                List<Role> role = new ArrayList<>();
                role.add(user.getRoleId());

                String token = jwtProvider.createToken(email, role);
                Map<Object, Object> response = new HashMap<>();
                response.put("email", email);
                response.put("role", role);
                response.put("token", token);
                return ResponseEntity.ok(response);

            }
        } else {
            throw new BadCredentialsException("User not found!");
        }
    }
}