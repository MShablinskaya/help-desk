package com.innowise.training.shablinskaya.helpdesk.dto;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import com.innowise.training.shablinskaya.helpdesk.enums.State;
import com.innowise.training.shablinskaya.helpdesk.enums.Urgency;

import java.sql.Timestamp;
import java.util.Objects;

public class TicketDto {

    private Long id;
    private String name;
    private String description;
    private Timestamp date;
    private Timestamp resolutionDate;
    private User owner;
    private User assignee;
    private User approve;
    private State state;
    private Category category;
    private Urgency urgency;

    public TicketDto(){}

    public TicketDto(Long id, String name, String description, Timestamp date, Timestamp resolutionDate, User owner, User assignee, User approve, State state, Category category, Urgency urgency) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.resolutionDate = resolutionDate;
        this.owner = owner;
        this.assignee = assignee;
        this.approve = approve;
        this.state = state;
        this.category = category;
        this.urgency = urgency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDto ticketDto = (TicketDto) o;
        return name.equals(ticketDto.name) &&
                Objects.equals(description, ticketDto.description) &&
                date.equals(ticketDto.date) &&
                Objects.equals(resolutionDate, ticketDto.resolutionDate) &&
                owner.equals(ticketDto.owner) &&
                Objects.equals(assignee, ticketDto.assignee) &&
                Objects.equals(approve, ticketDto.approve) &&
                state == ticketDto.state &&
                category.equals(ticketDto.category) &&
                urgency == ticketDto.urgency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, date, resolutionDate, owner, assignee, approve, state, category, urgency);
    }

    @Override
    public String toString() {
        return "TicketDto{" +
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
                '}';
    }
}
