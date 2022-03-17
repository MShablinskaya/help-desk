package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
    private static final String NOREPLY_ADRESS = "pr.mogilev.shablinskaya@gmail.com";
    private static final String RECIPIENT = "marie.macheri@gmail.com";
    private static final String SUBJECT = "New ticket for approval";
    private static final String BODY = "Dear Managers,\n" +
            "\n" +
            "New ticket [Ticket ID via link] is waiting for your approval.";


    private JavaMailSender javaMailSender;
    private SimpleMailMessage template;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SimpleMailMessage template) {
        this.javaMailSender = javaMailSender;
        this.template = template;
    }

    @Override
    public void sendSimpleMailMessage(String state) {
        try{
            if (state.equals("NEW")){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(NOREPLY_ADRESS);
            simpleMailMessage.setTo(RECIPIENT);
            simpleMailMessage.setSubject(SUBJECT);
            simpleMailMessage.setText(BODY);

            javaMailSender.send(simpleMailMessage);}else{
                throw new TicketStateException("State!");
            }
        }catch (MailException | TicketStateException exception){
            exception.printStackTrace();
        }

    }
}
