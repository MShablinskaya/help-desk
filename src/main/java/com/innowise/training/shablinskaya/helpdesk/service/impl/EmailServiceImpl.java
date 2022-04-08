package com.innowise.training.shablinskaya.helpdesk.service.impl;

import com.innowise.training.shablinskaya.helpdesk.dto.TicketDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.exception.TicketStateException;
import com.innowise.training.shablinskaya.helpdesk.service.EmailService;
import com.innowise.training.shablinskaya.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String NOREPLY_ADRESS = "pr.mogilev.shablinskaya@gmail.com";
    private static final String SBJ_CREATOR_DECLINED = "Ticket was declined";
    private static final String SBJ_CREATOR_CANCELLED = "Ticket was cancelled";
    private static final String SBJ_CREATOR_DONE = "Ticket was done";
    private static final String SBJ_FOR_MANAGERS = "New ticket for approval";
    private static final String SBJ_FOR_ENGINEERS = "Ticket was approved";
    private static final String SBJ_FOR_ASSIGNEE = "Feedback was provided";
    private static final String NEW_TICKET = "html/new-ticket.html";
    private static final String TICKET_APPROVED = "html/ticket-was-approved.html";
    private static final String TICKET_DECLINED = "html/ticket-declined.html";
    private static final String TICKET_CANCELLED_BY_MANAGER = "html/cancelled-by-manager.html";
    private static final String TICKET_CANCELLED_BY_ENGINEER = "html/approved-to-cancel.html";
    private static final String TICKET_WAS_DONE = "html/ticket-was-done.html";
    private static final String FEEDBACK_WAS_PROVIDED = "html/feedback-was-provided.html";

    private static final String APPROVED = "APPROVED";
    private static final String CANCELLED = "CANCELLED";
    private static final String DECLINED = "DECLINED";
    private static final String NEW = "NEW";
    private static final String DONE = "DONE";


    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserService userService;
    private final ThreadPoolExecutor emailExecutor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,TemplateEngine templateEngine, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.userService = userService;
    }

    @Override
    public void sendAllManagerMessage(TicketDto dto) {
        emailExecutor.execute(() -> {
            try {
                templateForManagerMails(dto);
            } catch (MessagingException | TicketStateException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void sendAllEngineerMessage(TicketDto dto) {
        emailExecutor.execute(() -> {
            try {
                templateForEngineerMails(dto);
            } catch (MessagingException | TicketStateException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void sendCreatorMessage(TicketDto dto) {
        emailExecutor.execute(() -> {
            try {
                templateForCreatorMails(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void sendEmailsForNewTickets(TicketDto dto) {
        emailExecutor.execute(() -> {
            try {
                if (dto.getState().equals(APPROVED)) {
                    sendAllEngineerMessage(dto);
                } else {
                    sendCreatorMessage(dto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void sendApproveMessage(TicketDto dto) {
        emailExecutor.execute(() -> {
            try {
                templateForCancelledTickets(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void sendAssigneeMessage(TicketDto dto) {
        emailExecutor.execute(() -> {
            try {
                templateForAssignee(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    private String[] engineersRecipients(TicketDto dto) throws TicketStateException {
        List<User> users = userService.getAllByRole(Role.valueOf("ENGINEER"));

        List<String> emails = new ArrayList<>();

        if (users != null) {
            users.forEach(user -> emails.add(user.getEmail()));
        }
        String email = dto.getOwner().getEmail();
        emails.add(email);

        return emails.toArray(new String[0]);
    }

    private String[] managerRecipients() throws TicketStateException {
        List<User> users = userService.getAllByRole(Role.valueOf("MANAGER"));

        List<String> emails = new ArrayList<>();

        if (users != null) {
            users.forEach(user -> emails.add(user.getEmail()));
        }

        return emails.toArray(new String[0]);
    }

    private void templateForManagerMails(TicketDto dto) throws MessagingException, TicketStateException {
        Long ticketName = dto.getId();

        Context context = new Context();
        context.setVariable("ticketName", ticketName);
        if (dto.getState().equals(NEW)) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            message.setFrom(NOREPLY_ADRESS);
            message.setTo(managerRecipients());
            message.setSubject(SBJ_FOR_MANAGERS);

            String htmlContent = templateEngine.process(NEW_TICKET, context);
            message.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        }


    }

    private void templateForEngineerMails(TicketDto dto) throws MessagingException, TicketStateException {
        Long ticketName = dto.getId();

        Context context = new Context();
        context.setVariable("ticketName", ticketName);
        if (dto.getState().equals(APPROVED)) {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            message.setFrom(NOREPLY_ADRESS);
            message.setTo(engineersRecipients(dto));
            message.setSubject(SBJ_FOR_ENGINEERS);

            String htmlContent = templateEngine.process(TICKET_APPROVED, context);
            message.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        }
    }

    private void templateForCreatorMails(TicketDto dto) {
        User user = userService.findByEmail(dto.getOwner().getEmail());
        String creator = dto.getOwner().getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Long ticketName = dto.getId();

        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("lastName", lastName);
        context.setVariable("ticketName", ticketName);

        if (dto.getState().equals(DECLINED)) {
            sendEmail(creator, context, SBJ_CREATOR_DECLINED, TICKET_DECLINED);
        } else if (dto.getState().equals(DONE)) {
            sendEmail(creator, context, SBJ_CREATOR_DONE, TICKET_WAS_DONE);
        } else {
            sendEmail(creator, context, SBJ_CREATOR_CANCELLED, TICKET_CANCELLED_BY_MANAGER);
        }
    }

    private void templateForCancelledTickets(TicketDto dto) {
        String approveEmail = dto.getApprove().getEmail();
        String ownerEmail = dto.getOwner().getEmail();
        Long ticketName = dto.getId();
        Context context = new Context();
        context.setVariable("ticketName", ticketName);
        if (dto.getState().equals(CANCELLED)) {
            sendEmail(approveEmail, context, SBJ_CREATOR_CANCELLED, TICKET_CANCELLED_BY_ENGINEER);
            sendEmail(ownerEmail, context, SBJ_CREATOR_CANCELLED, TICKET_CANCELLED_BY_ENGINEER);
        }
    }

    private void templateForAssignee(TicketDto dto) {
        String assignee = dto.getAssignee().getEmail();
        String firstName = dto.getAssignee().getFirstName();
        String lastName = dto.getAssignee().getLastName();
        Long ticketName = dto.getId();
        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("lastName", lastName);
        context.setVariable("ticketName", ticketName);
        sendEmail(assignee, context, SBJ_FOR_ASSIGNEE, FEEDBACK_WAS_PROVIDED);
    }

    private void sendEmail(String recipient, Context context, String sbjForRecipient, String messageBody) {
        try {
            if (recipient != null) {

                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                message.setFrom(NOREPLY_ADRESS);
                message.setTo(recipient);
                message.setSubject(sbjForRecipient);
                String htmlContent = templateEngine.process(messageBody, context);
                message.setText(htmlContent, true);
                javaMailSender.send(mimeMessage);

            } else {
                throw new TicketStateException("Ticket doesn't exist");
            }

        } catch (TicketStateException | MessagingException e) {
            e.printStackTrace();
        }
    }

}
