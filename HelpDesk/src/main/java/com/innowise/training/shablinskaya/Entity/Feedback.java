package com.innowise.training.shablinskaya.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long feedbackId;

    @Column(name = "USER_ID", nullable = false)
    private Long feedbackUserId;

    @Column(name = "RATE", nullable = false)
    private Long feedbackRate;

    @Column(name = "DATE", nullable = false)
    private Date feedbackDate;

    @Column(name = "TEXT", nullable = false)
    private String feedbacktext;

    @Column(name = "TICKET_ID", nullable = false)
    private Long feedbackTicketId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User userFeedback;

    @OneToOne
    @JoinColumn(name = "TICKET_ID", unique = true, nullable = false)
    private Ticket ticket;

    public Feedback() {
    }

    public Feedback(Long feedbackUserId, Long feedbackRate, Date feedbackDate, String feedbacktext, Long feedbackTicketId) {
        this.feedbackUserId = feedbackUserId;
        this.feedbackRate = feedbackRate;
        this.feedbackDate = feedbackDate;
        this.feedbacktext = feedbacktext;
        this.feedbackTicketId = feedbackTicketId;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackUserId() {
        return feedbackUserId;
    }

    public void setFeedbackUserId(Long feedbackUserId) {
        this.feedbackUserId = feedbackUserId;
    }

    public Long getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(Long feedbackRate) {
        this.feedbackRate = feedbackRate;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getFeedbacktext() {
        return feedbacktext;
    }

    public void setFeedbacktext(String feedbacktext) {
        this.feedbacktext = feedbacktext;
    }

    public Long getFeedbackTicketId() {
        return feedbackTicketId;
    }

    public void setFeedbackTicketId(Long feedbackTicketId) {
        this.feedbackTicketId = feedbackTicketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;
        Feedback feedback = (Feedback) o;
        return getFeedbackUserId().equals(feedback.getFeedbackUserId()) &&
                getFeedbackRate().equals(feedback.getFeedbackRate()) &&
                getFeedbackDate().equals(feedback.getFeedbackDate()) &&
                getFeedbacktext().equals(feedback.getFeedbacktext()) &&
                getFeedbackTicketId().equals(feedback.getFeedbackTicketId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFeedbackUserId(), getFeedbackRate(), getFeedbackDate(), getFeedbacktext(), getFeedbackTicketId());
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", feedbackUserId=" + feedbackUserId +
                ", feedbackRate=" + feedbackRate +
                ", feedbackDate=" + feedbackDate +
                ", feedbacktext='" + feedbacktext + '\'' +
                ", feedbackTicketId=" + feedbackTicketId +
                '}';
    }
}
