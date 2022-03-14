package com.innowise.training.shablinskaya.helpdesk.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long feedbackId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User userId;

    @Column(name = "RATE")
    private Long feedbackRate;

    @Column(name = "DATE")
    private Timestamp feedbackDate;

    @Column(name = "TEXT")
    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TICKET_ID", referencedColumnName = "id")
    private Ticket ticket;

    public Feedback() {
    }

    public Feedback(User userId, java.lang.Long feedbackRate, Timestamp feedbackDate, String text, Ticket ticket) {
        this.userId = userId;
        this.feedbackRate = feedbackRate;
        this.feedbackDate = feedbackDate;
        this.text = text;
        this.ticket = ticket;
    }

    public java.lang.Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(java.lang.Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public java.lang.Long getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(java.lang.Long feedbackRate) {
        this.feedbackRate = feedbackRate;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Ticket getFeedbackTicketId() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(feedbackId, feedback.feedbackId)
                && Objects.equals(userId, feedback.userId)
                && Objects.equals(feedbackRate, feedback.feedbackRate)
                && Objects.equals(feedbackDate, feedback.feedbackDate)
                && Objects.equals(text, feedback.text)
                && Objects.equals(ticket, feedback.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId, userId, feedbackRate, feedbackDate, text, ticket);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", feedbackUserId=" + userId +
                ", feedbackRate=" + feedbackRate +
                ", feedbackDate=" + feedbackDate +
                ", feedbacktext='" + text + '\'' +
                ", feedbackTicketId=" + ticket +
                '}';
    }
}
