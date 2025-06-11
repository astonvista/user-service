package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.User;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository userRepository;

    public UserDto create(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        return mapper.toDto(userRepository.save(user));
    }

    public UserDto getById(Long id) {
        return mapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь с ID " + id + " не найден")));
    }

    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));

        if (user.getName().equals(userDto.getName())
                && user.getEmail().equals(userDto.getEmail())
                && user.getAge() == userDto.getAge()) {
            throw new EntityExistsException("Введенные поля не отличаются от существующих");
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        return mapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> getAll() {
        List<UserDto> list = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            list.add(mapper.toDto(user));
        }
        return list;
    }
}
