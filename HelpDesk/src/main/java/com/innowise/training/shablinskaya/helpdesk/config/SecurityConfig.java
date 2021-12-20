package com.innowise.training.shablinskaya.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.inMemoryAuthentication()
                .withUser("employee").password(passwordEncoder().encode("password"))
                .roles("EMPLOYEE")
                .and()
                .withUser("engineer").password(passwordEncoder().encode("password"))
                .roles("EMPLOYEE", "ENGINEER")
                .and()
                .withUser("manager").password(passwordEncoder().encode("password"))
                .roles("MANAGER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home").access("hasRole('EMPLOYEE')")
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/engineer/**").hasRole("ENGINEER")
                .and()
                .formLogin();
    }

}
