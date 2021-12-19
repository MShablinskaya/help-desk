package com.innowise.training.shablinskaya.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DynamicInsert//зачем тебе это?
@DynamicUpdate//зачемтебе это?
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long commentId;//просто it

    @Column(name = "USER_ID", nullable = false)
    private Long commentUserId;//лишнее

    @Column(name = "TEXT", nullable = false)
    private String commentText;//просто text

    @Column(name = "TICKET_ID", nullable = false)
    private Long commentTicketId;//лишнее
    //где дата?

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TICKET_ID", nullable = false)
    private Ticket ticket;

    public Comment() {
    }

    public Comment(Long commentUserId, String commentText, Long commentTicketId) {
        this.commentUserId = commentUserId;
        this.commentText = commentText;
        this.commentTicketId = commentTicketId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Long commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Long getCommentTicketId() {
        return commentTicketId;
    }

    public void setCommentTicketId(Long commentTicketId) {
        this.commentTicketId = commentTicketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return getCommentUserId().equals(comment.getCommentUserId()) &&
                getCommentText().equals(comment.getCommentText()) &&
                getCommentTicketId().equals(comment.getCommentTicketId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentUserId(), getCommentText(), getCommentTicketId());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentUserId=" + commentUserId +
                ", commentText='" + commentText + '\'' +
                ", commentTicketId=" + commentTicketId +
                '}';
    }
}
