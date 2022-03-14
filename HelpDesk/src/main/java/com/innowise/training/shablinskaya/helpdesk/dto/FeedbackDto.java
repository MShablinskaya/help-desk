package com.innowise.training.shablinskaya.helpdesk.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class FeedbackDto {
    private Long id;
    private Long userId;
    private Long rate;
    private Timestamp date;
    private String text;
    private Long ticketId;

    public FeedbackDto() {
    }

    public FeedbackDto(Long id, Long userId, Long rate, Timestamp date, String text, Long ticketId) {
        this.id = id;
        this.userId = userId;
        this.rate = rate;
        this.date = date;
        this.text = text;
        this.ticketId = ticketId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
        FeedbackDto that = (FeedbackDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(userId, that.userId)
                && Objects.equals(rate, that.rate)
                && Objects.equals(date, that.date)
                && Objects.equals(text, that.text)
                && Objects.equals(ticketId, that.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, rate, date, text, ticketId);
    }

    @Override
    public String toString() {
        return "FeedbackDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", rate=" + rate +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
