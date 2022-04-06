package com.innowise.training.shablinskaya.helpdesk.dto;

import com.innowise.training.shablinskaya.helpdesk.entity.Category;
import com.innowise.training.shablinskaya.helpdesk.entity.User;

import java.sql.Timestamp;
import java.util.Objects;
public class TicketDto {

    private Long id;
    private String name;
    private String description;
    private Timestamp creationDate;
    private Timestamp resolutionDate;
    private UserDto owner;
    private UserDto assignee;
    private UserDto approve;
    private String state;
    private String category;
    private String urgency;

    public TicketDto() {
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Timestamp resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public UserDto getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDto assignee) {
        this.assignee = assignee;
    }

    public UserDto getApprove() {
        return approve;
    }

    public void setApprove(UserDto approve) {
        this.approve = approve;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
        TicketDto dto = (TicketDto) o;
        return Objects.equals(id, dto.id)
                && Objects.equals(name, dto.name)
                && Objects.equals(description, dto.description)
                && Objects.equals(creationDate, dto.creationDate)
                && Objects.equals(resolutionDate, dto.resolutionDate)
                && Objects.equals(owner, dto.owner)
                && Objects.equals(assignee, dto.assignee)
                && Objects.equals(approve, dto.approve)
                && Objects.equals(state, dto.state)
                && Objects.equals(category, dto.category)
                && Objects.equals(urgency, dto.urgency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, creationDate, resolutionDate, owner, assignee, approve, state, category, urgency);
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
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
