package com.innowise.training.shablinskaya.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private java.lang.Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String description;

    @Column(name = "CREATED_ON")
    private Timestamp date;

    @Column(name = "DESIRED_RESOLUTION_DATE")
    private Timestamp resolutionDate;

    @JoinColumn(name = "OWNER_ID")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User owner;

    @JoinColumn(name = "ASSIGNEE_ID")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User assignee;

    @JoinColumn(name = "APPROVE_ID")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User approve;

    @Column(name = "STATE_ID")
    @Enumerated(EnumType.STRING)
    private State state;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Category category;

    @Column(name = "URGENCY_ID")
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticketId")
    private Set<History> historySet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticketId")
    private Set<Attachment> attachmentSet;

    public Ticket() {
    }

    public Ticket(java.lang.Long id, String name, String description, Timestamp date, Timestamp resolutionDate, User owner, User assignee, User approve, State  state, Urgency urgency){
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.resolutionDate = resolutionDate;
        this.owner = owner;
        this.assignee = assignee;
        this.approve = approve;
        this.state = state;
        this.urgency = urgency;
    }

    public java.lang.Long getId() {
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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

    public Set<History> getHistorySet() {
        return historySet;
    }

    public void setHistorySet(Set<History> historySet) {
        this.historySet = historySet;
    }

    public Set<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(Set<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return getId().equals(ticket.getId()) &&
                getName().equals(ticket.getName()) &&
                getDescription().equals(ticket.getDescription()) &&
                getDate().equals(ticket.getDate()) &&
                Objects.equals(getResolutionDate(), ticket.getResolutionDate()) &&
                getOwner().equals(ticket.getOwner()) &&
                Objects.equals(getAssignee(), ticket.getAssignee()) &&
                Objects.equals(getApprove(), ticket.getApprove()) &&
                getState() == ticket.getState() &&
                getCategory().equals(ticket.getCategory()) &&
                getUrgency() == ticket.getUrgency() &&
                Objects.equals(getHistorySet(), ticket.getHistorySet()) &&
                Objects.equals(getAttachmentSet(), ticket.getAttachmentSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getDate(), getResolutionDate(), getOwner(), getAssignee(), getApprove(), getState(), getCategory(), getUrgency(), getHistorySet(), getAttachmentSet());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", resolutionDate=" + resolutionDate +
                ", owner=" + owner +
                ", assignee=" + assignee +
                ", approve=" + approve +
                ", state=" + state +
                ", category=" + category +
                ", urgency=" + urgency +
                ", historySet=" + historySet +
                ", attachmentSet=" + attachmentSet +
                '}';
    }
}
