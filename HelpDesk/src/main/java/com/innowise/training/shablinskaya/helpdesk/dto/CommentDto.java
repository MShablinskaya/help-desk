package com.innowise.training.shablinskaya.helpdesk.dto;

import java.util.Objects;

public class CommentDto {
    private Long id;
    private Long userId;
    private String text;
    private Long ticketId;

    public CommentDto() {
    }

    public CommentDto(Long id, Long userId, String text, Long ticketId) {
        this.id = id;
        this.userId = userId;
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
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(userId, that.userId)
                && Objects.equals(text, that.text)
                && Objects.equals(ticketId, that.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, text, ticketId);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
