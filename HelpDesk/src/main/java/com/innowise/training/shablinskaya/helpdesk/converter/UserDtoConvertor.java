package com.innowise.training.shablinskaya.helpdesk.converter;

import com.innowise.training.shablinskaya.helpdesk.dto.UserDto;
import com.innowise.training.shablinskaya.helpdesk.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConvertor {

    public UserDto toDto(User user){
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public User toEntity(UserDto dto){
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

}
