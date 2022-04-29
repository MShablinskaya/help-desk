package com.innowise.training.shablinskaya.helpdesk.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class HistoryDto {
    private Timestamp date;
    private UserDto userDto;
    private String action;
    private String description;

    public HistoryDto() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryDto that = (HistoryDto) o;
        return Objects.equals(date, that.date)
                && Objects.equals(userDto, that.userDto)
                && Objects.equals(action, that.action)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, userDto, action, description);
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "date=" + date +
                ", userDto=" + userDto +
                ", action='" + action + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
