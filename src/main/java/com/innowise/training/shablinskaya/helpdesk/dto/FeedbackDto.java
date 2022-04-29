package com.innowise.training.shablinskaya.helpdesk.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class FeedbackDto {

    private Long rate;
    private String text;
    private Long ticketId;

    public FeedbackDto() {
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
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
        return Objects.equals(rate, that.rate)
                && Objects.equals(text, that.text)
                && Objects.equals(ticketId, that.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, text, ticketId);
    }

    @Override
    public String toString() {
        return "FeedbackDto{" +
                "rate=" + rate +
                ", text='" + text + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
