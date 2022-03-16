package com.innowise.training.shablinskaya.helpdesk.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User userId;

    @Column(name = "TEXT", length = 500)
    private String comment;

    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;


    public Comment() {
    }

    public Comment(User userId, String comment, Ticket ticket) {
        this.userId = userId;
        this.comment = comment;
        this.ticket = ticket;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String text) {
        this.comment = text;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return getUserId().equals(comment.getUserId()) &&
                getComment().equals(comment.getComment()) &&
                getTicket().equals(comment.getTicket());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getComment(), getTicket());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + id +
                ", commentUserId=" + userId +
                ", commentText='" + comment + '\'' +
                ", commentTicketId=" + ticket +
                '}';
    }
}
