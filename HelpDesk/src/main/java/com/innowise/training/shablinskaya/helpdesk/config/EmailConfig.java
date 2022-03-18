package com.innowise.training.shablinskaya.helpdesk.config;


import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailConfig {
    private static final String NOREPLY_ADRESS = "pr.mogilev.shablinskaya@gmail.com";

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername(NOREPLY_ADRESS);
        javaMailSender.setPassword("MarieMacheri&22&04&1992");

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return  javaMailSender;
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText("This is the test email template for your email:\\n%s\\n");

        return simpleMailMessage;
    }
}
