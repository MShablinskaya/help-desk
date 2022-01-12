package com.innowise.training.shablinskaya.helpdesk.service;


public interface SecurityService {

    String findLoggedUsername();

    void autoLogin(String email, String password);
}
