package com.innowise.training.shablinskaya.helpdesk.converter.impl;

import com.innowise.training.shablinskaya.helpdesk.converter.UserConverter;
import com.innowise.training.shablinskaya.helpdesk.dto.UserDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();

        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }
}
