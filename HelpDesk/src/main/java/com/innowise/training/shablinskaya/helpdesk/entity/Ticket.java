package com.innowise.training.shablinskaya.helpdesk.entity;

import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_Date")
    private Timestamp createDate;

    @Column(name = "resolution_Date")
    private Timestamp resolutionDate;

    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;

    @JoinColumn(name = "assignee_id")
    @ManyToOne
    private User assignee;

    @JoinColumn(name = "approve_id")
    @ManyToOne
    private User approve;

    @Column(name = "state_id")
    @Enumerated(EnumType.STRING)
    private State state;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @Column(name = "urgency_id")
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @OneToMany(mappedBy = "ticket")
    private List<History> histories = new ArrayList<>();

    @OneToOne(mappedBy = "ticket")
    private Feedback feedback;

    @OneToMany(mappedBy = "ticket")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "ticket")
    private List<Attachment> attachments = new ArrayList<>();

    public Ticket() {
    }

    public Ticket(String name, String description, Timestamp createDate, Timestamp resolutionDate, User owner, User assignee, User approve, State state, Urgency urgency, Category category) {
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.resolutionDate = resolutionDate;
        this.owner = owner;
        this.assignee = assignee;
        this.approve = approve;
        this.state = state;
        this.urgency = urgency;
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Timestamp resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getApprove() {
        return approve;
    }

    public void setApprove(User approve) {
        this.approve = approve;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(name, ticket.name)
                && Objects.equals(description, ticket.description)
                && Objects.equals(createDate, ticket.createDate)
                && Objects.equals(resolutionDate, ticket.resolutionDate)
                && Objects.equals(owner, ticket.owner)
                && Objects.equals(assignee, ticket.assignee)
                && Objects.equals(approve, ticket.approve)
                && state == ticket.state
                && Objects.equals(category, ticket.category) && urgency == ticket.urgency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createDate, resolutionDate, owner, assignee, approve, state, category, urgency);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", resolutionDate=" + resolutionDate +
                ", owner=" + owner +
                ", assignee=" + assignee +
                ", approve=" + approve +
                ", state=" + state +
                ", category=" + category +
                ", urgency=" + urgency +
                ", histories=" + histories +
                ", feedback=" + feedback +
                ", attachments=" + attachments +
                '}';
    }
}