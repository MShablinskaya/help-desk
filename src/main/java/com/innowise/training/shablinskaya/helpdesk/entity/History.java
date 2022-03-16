package com.innowise.training.shablinskaya.helpdesk.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "History")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "action")
    private String action;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User userId;

    @Column(name = "description")
    private String description;

    public History() {
    }

    public History(Ticket ticket, Timestamp date, String action, User userId, String description) {
        this.ticket = ticket;
        this.date = date;
        this.action = action;
        this.userId = userId;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(ticket, history.ticket)
                && Objects.equals(date, history.date)
                && Objects.equals(action, history.action)
                && Objects.equals(userId, history.userId)
                && Objects.equals(description, history.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticket, date, action, userId, description);
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", date=" + date +
                ", action='" + action + '\'' +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                '}';
    }
}
