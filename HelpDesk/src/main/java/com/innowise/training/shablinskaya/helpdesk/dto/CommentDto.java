package com.innowise.training.shablinskaya.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Objects;

public class CommentDto {

    private Timestamp creationDate;
    private UserDto userId;
    private String text;
    @JsonIgnore
    private Long ticketId;

    public CommentDto() {
    }

    public UserDto getUserId() {
        return userId;
    }

    public void setUserId(UserDto userId) {
        this.userId = userId;
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto dto = (CommentDto) o;
        return Objects.equals(creationDate, dto.creationDate)
                && Objects.equals(userId, dto.userId)
                && Objects.equals(text, dto.text)
                && Objects.equals(ticketId, dto.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, userId, text, ticketId);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "creationDate=" + creationDate +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
