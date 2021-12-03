package com.innowise.training.shablinskaya.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "HISTORY")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long historyId;

    @Column(name = "TICKET_ID", nullable = false)
    private Long historyTicketId;

    @Column(name = "DATE", nullable = false)
    private Date ticketUploadDate;

    @Column(name = "ACTION", nullable = false)
    private String actionOnTicket;

    @Column(name = "USER_ID", nullable = false)
    private Long historyUserId;

    @Column(name = "DESCRIPTION", nullable = false)
    private String actionOnTicketDescription;

    public History() {
    }

    public History(Long historyTicketId, Date ticketUploadDate, String actionOnTicket, Long historyUserId, String actionOnTicketDescription) {
        this.historyTicketId = historyTicketId;
        this.ticketUploadDate = ticketUploadDate;
        this.actionOnTicket = actionOnTicket;
        this.historyUserId = historyUserId;
        this.actionOnTicketDescription = actionOnTicketDescription;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getHistoryTicketId() {
        return historyTicketId;
    }

    public void setHistoryTicketId(Long historyTicketId) {
        this.historyTicketId = historyTicketId;
    }

    public Date getTicketUploadDate() {
        return ticketUploadDate;
    }

    public void setTicketUploadDate(Date ticketUploadDate) {
        this.ticketUploadDate = ticketUploadDate;
    }

    public String getActionOnTicket() {
        return actionOnTicket;
    }

    public void setActionOnTicket(String actionOnTicket) {
        this.actionOnTicket = actionOnTicket;
    }

    public Long getHistoryUserId() {
        return historyUserId;
    }

    public void setHistoryUserId(Long historyUserId) {
        this.historyUserId = historyUserId;
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
        return getHistoryTicketId().equals(history.getHistoryTicketId()) &&
                getTicketUploadDate().equals(history.getTicketUploadDate()) &&
                getActionOnTicket().equals(history.getActionOnTicket()) &&
                getHistoryUserId().equals(history.getHistoryUserId()) &&
                getActionOnTicketDescription().equals(history.getActionOnTicketDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHistoryTicketId(), getTicketUploadDate(), getActionOnTicket(), getHistoryUserId(), getActionOnTicketDescription());
    }

    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", historyTicketId=" + historyTicketId +
                ", ticketUploadDate=" + ticketUploadDate +
                ", actionOnTicket='" + actionOnTicket + '\'' +
                ", historyUserId=" + historyUserId +
                ", actionOnTicketDescription='" + actionOnTicketDescription + '\'' +
                '}';
    }
}
