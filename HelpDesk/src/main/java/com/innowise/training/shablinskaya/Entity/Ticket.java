package com.innowise.training.shablinskaya.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long ticketId;

    @Column(name = "NAME", nullable = false)
    private String ticketName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String ticketDescription;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_ON", nullable = false)
    private Date ticketDate;

    @Column(name = "DESIRED_RESOLUTION_DATE", nullable = false)
    private Date ticketResolutionDate;

    @Column(name = "ASSIGNEE_ID", nullable = false)
    private Long assigneeId;//?

    @Column(name = "OWNER_ID", nullable = false)
    private Long ownerId;//?

    @Column(name = "STATE_ID", nullable = false)
    private String stateId;

    @Column(name = "CATEGORY_ID", nullable = false)
    private Long ticketCategoryId;

    @Column(name = "URGENCY_ID", nullable = false)
    private String urgencyId;

    @Column(name = "APPROVE_ID", nullable = false)
    private Long approveId;//?

    /*Зачем тебе ManyToMany? у тебя есть поля owner, assignee, approver это и есть наши пользователи
     и у тикета будет только один такой, я тебе это уже рассказывал*/
    @ManyToMany
    @JoinTable(name = "USERS_TICKETS",
            joinColumns = @JoinColumn(name = "TICKET_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    )
    private Set<User> user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
    private Set<History> historySet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
    private Set<Attachment> attachmentSet;

    public Ticket() {
    }

    public Ticket(String ticketName, String ticketDescription, Date ticketDate, Date ticketResolutionDate, Long assigneeId, Long ownerId, String stateId, Long ticketCategoryId, String urgencyId, Long approveId) {
        this.ticketName = ticketName;
        this.ticketDescription = ticketDescription;
        this.ticketDate = ticketDate;
        this.ticketResolutionDate = ticketResolutionDate;
        this.assigneeId = assigneeId;
        this.ownerId = ownerId;
        this.stateId = stateId;
        this.ticketCategoryId = ticketCategoryId;
        this.urgencyId = urgencyId;
        this.approveId = approveId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public Date getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(Date ticketDate) {
        this.ticketDate = ticketDate;
    }

    public Date getTicketResolutionDate() {
        return ticketResolutionDate;
    }

    public void setTicketResolutionDate(Date ticketResolutionDate) {
        this.ticketResolutionDate = ticketResolutionDate;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Long getTicketCategoryId() {
        return ticketCategoryId;
    }

    public void setTicketCategoryId(Long ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public String getUrgencyId() {
        return urgencyId;
    }

    public void setUrgencyId(String urgencyId) {
        this.urgencyId = urgencyId;
    }

    public Long getApproveId() {
        return approveId;
    }

    public void setApproveId(Long approveId) {
        this.approveId = approveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return getTicketName().equals(ticket.getTicketName()) &&
                getTicketDescription().equals(ticket.getTicketDescription()) &&
                getTicketDate().equals(ticket.getTicketDate()) &&
                getTicketResolutionDate().equals(ticket.getTicketResolutionDate()) &&
                getAssigneeId().equals(ticket.getAssigneeId()) &&
                getOwnerId().equals(ticket.getOwnerId()) &&
                getStateId().equals(ticket.getStateId()) &&
                getTicketCategoryId().equals(ticket.getTicketCategoryId()) &&
                getUrgencyId().equals(ticket.getUrgencyId()) &&
                getApproveId().equals(ticket.getApproveId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicketName(), getTicketDescription(), getTicketDate(), getTicketResolutionDate(), getAssigneeId(), getOwnerId(), getStateId(), getTicketCategoryId(), getUrgencyId(), getApproveId());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", ticketName='" + ticketName + '\'' +
                ", ticketDescription='" + ticketDescription + '\'' +
                ", ticketDate=" + ticketDate +
                ", ticketResolutionDate=" + ticketResolutionDate +
                ", assigneeId=" + assigneeId +
                ", ownerId=" + ownerId +
                ", stateId='" + stateId + '\'' +
                ", ticketCategoryId=" + ticketCategoryId +
                ", urgencyId='" + urgencyId + '\'' +
                ", approveId=" + approveId +
                '}';
    }
}
