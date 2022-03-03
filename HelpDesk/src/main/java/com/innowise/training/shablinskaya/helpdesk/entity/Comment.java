package com.innowise.training.shablinskaya.helpdesk.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    private User userId;

    @Column(name = "TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "TICKET_ID", nullable = false)
    private Ticket ticketId;


    public Comment() {
    }

    public Comment(User userId, String text, Ticket ticketId) {
        this.userId = userId;
        this.text = text;
        this.ticketId = ticketId;
    }

    public java.lang.Long getId() {
        return id;
    }


    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public void setTicketId(Ticket ticket) {
        this.ticketId = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return getUserId().equals(comment.getUserId()) &&
                getText().equals(comment.getText()) &&
                getTicketId().equals(comment.getTicketId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getText(), getTicketId());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + id +
                ", commentUserId=" + userId +
                ", commentText='" + text + '\'' +
                ", commentTicketId=" + ticketId +
                '}';
    }
}
