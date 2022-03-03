package com.innowise.training.shablinskaya.helpdesk.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class TicketDto {

    private String name;
    private String description;
    private Timestamp resolutionDate;
    private Long owner;
    private Long assignee;
    private Long approve;
    private String state;
    private Long category;
    private String urgency;

    public TicketDto() {
    }


    public TicketDto(String name, String description, Timestamp resolutionDate, Long owner, Long assignee, Long approve, String state, Long category, String urgency) {
        this.name = name;
        this.description = description;
        this.resolutionDate = resolutionDate;
        this.owner = owner;
        this.assignee = assignee;
        this.approve = approve;
        this.state = state;
        this.category = category;
        this.urgency = urgency;
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

    public Timestamp getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Timestamp resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    public Long getApprove() {
        return approve;
    }

    public void setApprove(Long approve) {
        this.approve = approve;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDto ticketDto = (TicketDto) o;
        return Objects.equals(name, ticketDto.name) &&
                Objects.equals(description, ticketDto.description) &&
                Objects.equals(resolutionDate, ticketDto.resolutionDate) &&
                Objects.equals(owner, ticketDto.owner) &&
                Objects.equals(assignee, ticketDto.assignee) &&
                Objects.equals(approve, ticketDto.approve) &&
                Objects.equals(state, ticketDto.state) &&
                Objects.equals(category, ticketDto.category) &&
                Objects.equals(urgency, ticketDto.urgency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, resolutionDate, owner, assignee, approve, state, category, urgency);
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", resolutionDate=" + resolutionDate +
                ", owner=" + owner +
                ", assignee=" + assignee +
                ", approve=" + approve +
                ", state='" + state + '\'' +
                ", category='" + category + '\'' +
                ", urgency='" + urgency + '\'' +
                '}';
    }
}
