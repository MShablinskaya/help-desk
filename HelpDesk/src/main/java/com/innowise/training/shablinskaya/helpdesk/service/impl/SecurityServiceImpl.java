//package com.innowise.training.shablinskaya.helpdesk.service.impl;
//
//import com.innowise.training.shablinskaya.helpdesk.service.SecurityService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SecurityServiceImpl implements SecurityService {
//
//    private AuthenticationManager authenticationManager;
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public SecurityServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService){
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public String findLoggedUsername() {
//        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
//        if(userDetails instanceof UserDetails){
//            return ((UserDetails) userDetails).getUsername();
//        }
//
//        return null;
//    }
//
//    @Override
//    public void autoLogin(String email, String password) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());
//        authenticationManager.authenticate(authenticationToken);
//
//        if(authenticationToken.isAuthenticated()){
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }
//
//    }
//}
