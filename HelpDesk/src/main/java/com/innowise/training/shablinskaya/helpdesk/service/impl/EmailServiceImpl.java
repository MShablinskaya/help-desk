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
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String NOREPLY_ADRESS = "pr.mogilev.shablinskaya@gmail.com";
    private static final String SBJ_CREATOR_DECLINED = "Ticket was declined";
    private static final String SBJ_CREATOR_CANCELLED = "Ticket was cancelled";
    private static final String SBJ_FOR_MANAGERS = "New ticket for approval";
    private static final String SBJ_FOR_ENGINEERS = "Ticket was approved";


    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserService userService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
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
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String ticketName = "Ticket #" + dto.getId();

        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("lastName", lastName);
        context.setVariable("ticketName", ticketName);


        try {
            if (creator != null) {


                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                message.setFrom(NOREPLY_ADRESS);
                message.setTo(creator);
                message.setSubject(SBJ_CREATOR_CANCELLED);

                String htmlContent = templateEngine.process("html/new-to-cancel.html", context);
                message.setText(htmlContent, true);
                javaMailSender.send(mimeMessage);

            } else {
                throw new TicketStateException("Ticket doesn't exist");
            }

        } catch (TicketStateException | MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendApproverMessage(TicketDto dto) {
        User user = userService.findById(dto.getApprove());
        String approve = user.getEmail();
        Context context = new Context();
        try {
            if (approve != null) {

                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                message.setFrom(NOREPLY_ADRESS);
                message.setTo(approve);
                message.setSubject(SBJ_CREATOR_CANCELLED);
                String htmlContent = templateEngine.process("html/forCreator", context);
                message.setText(htmlContent, true);
                javaMailSender.send(mimeMessage);

            } else {
                throw new TicketStateException("Ticket doesn't exist");
            }

        } catch (TicketStateException | MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendAssigneMessage(TicketDto dto) {

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


    private static final String BODY = "Dear [Recipient First Name] [Recipient Last Name],\n" +
            "\n" +
            "Ticket [Ticket ID via link] was cancelled by the Manager.";

}
