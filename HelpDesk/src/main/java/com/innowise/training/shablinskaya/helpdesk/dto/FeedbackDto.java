package com.innowise.training.shablinskaya.helpdesk.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class FeedbackDto {
    private Long id;
    private String userId;
    private Long rate;
    private Timestamp date;
    private String text;
    private Long ticketId;

    public FeedbackDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackDto dto = (FeedbackDto) o;
        return Objects.equals(id, dto.id)
                && Objects.equals(userId, dto.userId)
                && Objects.equals(rate, dto.rate)
                && Objects.equals(date, dto.date)
                && Objects.equals(text, dto.text)
                && Objects.equals(ticketId, dto.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, rate, date, text, ticketId);
    }

    @Override
    public String toString() {
        return "FeedbackDto{" +
                ", userId='" + userId + '\'' +
                ", rate=" + rate +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
