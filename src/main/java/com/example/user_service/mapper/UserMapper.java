package com.example.user_service.mapper;

import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(),
                user.getEmail(), user.getAge(), user.getCreatedAt());
    }

    public User toEntity(UserDto dto) {
        return new User(dto.getId(), dto.getName(),
                dto.getEmail(), dto.getAge(), dto.getCreatedAt());
    }
}
