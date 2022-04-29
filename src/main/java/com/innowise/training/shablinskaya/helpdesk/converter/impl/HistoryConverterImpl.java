package com.innowise.training.shablinskaya.helpdesk.converter.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.HistoryConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.HistoryDto;
import com.innowise.training.shablinskaya.helpdesk.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoryConverterImpl implements HistoryConverter {
    private final UserConverter userConverter;

    @Autowired
    public HistoryConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public HistoryDto toDto(History history) {
        HistoryDto dto = new HistoryDto();

        dto.setDate(history.getDate());
        dto.setUserDto(userConverter.toDto(history.getUserId()));
        dto.setAction(history.getAction());
        dto.setDescription(history.getDescription());

        return dto;
    }
}
