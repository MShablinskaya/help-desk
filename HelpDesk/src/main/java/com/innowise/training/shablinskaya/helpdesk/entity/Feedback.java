package com.innowise.training.shablinskaya.helpdesk.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity(name = "PUBLIC.FEEDBACK")
//@Table(name = "FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private java.lang.Long feedbackId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    private User userId;

    @Column(name = "RATE")
    private java.lang.Long feedbackRate;

    @Column(name = "DATE")
    private Timestamp feedbackDate;

    @Column(name = "TEXT")
    private String text;

    @OneToOne
    @JoinColumn(name = "TICKET_ID", unique = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;
        Feedback feedback = (Feedback) o;
        return getUserId().equals(feedback.getUserId()) &&
                getFeedbackRate().equals(feedback.getFeedbackRate()) &&
                getFeedbackDate().equals(feedback.getFeedbackDate()) &&
                getText().equals(feedback.getText()) &&
                getFeedbackTicketId().equals(feedback.getFeedbackTicketId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getFeedbackRate(), getFeedbackDate(), getText(), getFeedbackTicketId());
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
