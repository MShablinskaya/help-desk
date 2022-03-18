package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.EmailService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailServiceImpl implements EmailService {
    private static final String NOREPLY_ADRESS = "pr.mogilev.shablinskaya@gmail.com";
    private static final String SBJ_CREATOR_DECLINED = "Ticket was declined";
    private static final String SBJ_CREATOR_CANCELLED = "Ticket was cancelled";
    private static final String SBJ_FOR_MANAGERS = "New ticket for approval";
    private static final String SBJ_FOR_ENGINEERS = "Ticket was approved";



    private JavaMailSender javaMailSender;
    private SimpleMailMessage template;
    private UserService userService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SimpleMailMessage template, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.template = template;
        this.userService = userService;
    }

    @Override
    public void sendAllManagerMessage(TicketDto dto) {
        try {
            if (dto != null && dto.getState().equals("NEW")) {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

                simpleMailMessage.setFrom(NOREPLY_ADRESS);
                simpleMailMessage.setTo(managerRecipients());
                simpleMailMessage.setSubject(SBJ_FOR_MANAGERS);
                simpleMailMessage.setText(BODY);

                javaMailSender.send(simpleMailMessage);
            }
        } catch (MailException | TicketStateException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void sendAllEngineerMessage(TicketDto dto) {
        try {
            if (dto != null && dto.getState().equals("APPROVED")) {

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

                simpleMailMessage.setFrom(NOREPLY_ADRESS);
                simpleMailMessage.setTo(engineersRecipients());
                simpleMailMessage.setSubject(SBJ_FOR_MANAGERS);
                simpleMailMessage.setText(BODY);

                javaMailSender.send(simpleMailMessage);
            }
        } catch (MailException | TicketStateException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void sendCreatorMessage(TicketDto dto) {
        User user = userService.findById(dto.getOwner());
        String creator = user.getEmail();
        try{
            if (creator != null){
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

                simpleMailMessage.setFrom(NOREPLY_ADRESS);
                simpleMailMessage.setTo(creator);
                simpleMailMessage.setSubject(SBJ_FOR_MANAGERS);
                simpleMailMessage.setText(BODY);

                javaMailSender.send(simpleMailMessage);
            }else {
                throw new TicketStateException("Ticket doesn't exist");
            }

        } catch (TicketStateException e) {
            e.printStackTrace();
        }

    }

    private String[] engineersRecipients() throws TicketStateException {
        List<User> users = userService.getAllByRole(Role.valueOf("ENGINEER"));

        List<String> emails = new ArrayList<>();

        if (users != null) {
            users.forEach(user -> {
                emails.add(user.getEmail());
            });
        }

        return emails.toArray(new String[0]);
    }

    private String[] managerRecipients() throws TicketStateException {
        List<User> users = userService.getAllByRole(Role.valueOf("MANAGER"));

        List<String> emails = new ArrayList<>();

        if (users != null) {
            users.forEach(user -> {
                emails.add(user.getEmail());
            });
        }

        return emails.toArray(new String[0]);
    }

    private String textBodyForManager

    private static final String BODY = "Dear Managers,\n" +
            "\n" +
            "New ticket [Ticket ID via link] is waiting for your approval.";

}
