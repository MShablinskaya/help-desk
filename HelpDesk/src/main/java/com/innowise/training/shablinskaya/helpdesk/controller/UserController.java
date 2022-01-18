//package com.innowise.training.shablinskaya.helpdesk.controller;
//
//import com.innowise.training.shablinskaya.helpdesk.service.SecurityService;
//import com.innowise.training.shablinskaya.helpdesk.service.UserService;
//import com.innowise.training.shablinskaya.helpdesk.validator.UserValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
//public class UserController {
//
//    private UserService userService;
//    private SecurityService securityService;
//    private UserValidator userValidator;
//
//    @Autowired
//    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator) {
//        this.userService = userService;
//        this.securityService = securityService;
//        this.userValidator = userValidator;
//    }
//
//    @GetMapping(value = "/login")
//    public String login(Model model, String error, String logout) {
//        if (error != null) {
//            model.addAttribute("error", "E-mail or password is incorrect.");
//        }
//
//        if (logout != null) {
//            model.addAttribute("message", "Logged out successfully.");
//        }
//
//        return "login";
//    }
//
//    @GetMapping(value = "/welcome")
//    public String welcome(Model model) {
//        return "welcome";
//    }
//
//}
//
//
