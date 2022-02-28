package com.innowise.training.shablinskaya.helpdesk.entity;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity(name = "PUBLIC.HISTORY")
//@Table(name = "HISTORY")
@Proxy(lazy = false)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private java.lang.Long historyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticketId;

    @Column(name = "DATE")
    private Timestamp ticketUploadDate;

    @Column(name = "ACTION")
    private String actionOnTicket;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User userId;

    @Column(name = "DESCRIPTION")
    private String actionOnTicketDescription;


    public History() {
    }

    public History(Ticket ticketId, Timestamp ticketUploadDate, String actionOnTicket, User userId, String actionOnTicketDescription) {
        this.ticketId = ticketId;
        this.ticketUploadDate = ticketUploadDate;
        this.actionOnTicket = actionOnTicket;
        this.userId = userId;
        this.actionOnTicketDescription = actionOnTicketDescription;
    }

    public java.lang.Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(java.lang.Long historyId) {
        this.historyId = historyId;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public void setTicketId(Ticket ticket) {
        this.ticketId = ticket;
    }

    public Date getTicketUploadDate() {
        return ticketUploadDate;
    }

    public void setTicketUploadDate(Timestamp ticketUploadDate) {
        this.ticketUploadDate = ticketUploadDate;
    }

    public String getActionOnTicket() {
        return actionOnTicket;
    }

    public void setActionOnTicket(String actionOnTicket) {
        this.actionOnTicket = actionOnTicket;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getActionOnTicketDescription() {
        return actionOnTicketDescription;
    }

    public void setActionOnTicketDescription(String actionOnTicketDescription) {
        this.actionOnTicketDescription = actionOnTicketDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof History)) return false;
        History history = (History) o;
        return getTicketId().equals(history.getTicketId()) &&
                getTicketUploadDate().equals(history.getTicketUploadDate()) &&
                getActionOnTicket().equals(history.getActionOnTicket()) &&
                getUserId().equals(history.getUserId()) &&
                getActionOnTicketDescription().equals(history.getActionOnTicketDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicketId(), getTicketUploadDate(), getActionOnTicket(), getUserId(), getActionOnTicketDescription());
    }

    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", historyTicketId=" + ticketId +
                ", ticketUploadDate=" + ticketUploadDate +
                ", actionOnTicket='" + actionOnTicket + '\'' +
                ", historyUserId=" + userId +
                ", actionOnTicketDescription='" + actionOnTicketDescription + '\'' +
                '}';
    }


}
