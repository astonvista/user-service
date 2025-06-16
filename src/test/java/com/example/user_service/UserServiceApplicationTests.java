package com.example.user_service;

import com.example.user_service.controller.UserController;
import com.example.user_service.dto.UserDto;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureWebMvc
@WebMvcTest(UserController.class)
class UserServiceApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @TestConfiguration
    static class TestConfig {

        @Bean
        UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Test
    void getAll_returnsTwoElementList() throws Exception {
        List<UserDto> list = Arrays.asList(
                new UserDto(1L, "TestOne", "test1@test.com", 36, LocalDateTime.now()),
                new UserDto(2L, "TestTwo", "test2@test.com", 37, LocalDateTime.now()));

        when(userService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("TestOne"))
                .andExpect(jsonPath("$[0].email").value("test1@test.com"))
                .andExpect(jsonPath("$[0].age").value(36))
                .andExpect(jsonPath("$[1].name").value("TestTwo"))
                .andExpect(jsonPath("$[1].email").value("test2@test.com"))
                .andExpect(jsonPath("$[1].age").value(37));
    }

    @Test
    void getById_returnsUserDto() throws Exception {
        UserDto user = new UserDto(1L, "TestOne", "test1@test.com", 36, LocalDateTime.now());

        when(userService.getById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestOne"))
                .andExpect(jsonPath("$.email").value("test1@test.com"))
                .andExpect(jsonPath("$.age").value(36));
    }

    @Test
    void getById_shouldReturn404WhenUserNotFound() throws Exception {
        when(userService.getById(99L)).thenThrow(new UserNotFoundException("Пользователь с ID 99 не найден"));

        mockMvc.perform(get("/api/users/99")).andExpect(status().isNotFound());
    }

    @Test
    void create_shouldCreateUser() throws Exception {
        UserDto user = new UserDto(1L, "TestOne", "test1@test.com", 36, LocalDateTime.now());
        when(userService.create(any())).thenReturn(user);

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                          "name": "TestOne",
                          "email": "test1@test.com",
                          "age": 36
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("TestOne"))
                .andExpect(jsonPath("$.email").value("test1@test.com"))
                .andExpect(jsonPath("$.age").value(36));
    }

    @Test
    void create_shouldReturn400WhenInvalidUser() throws Exception {
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                          "name": "",
                          "email": "wrong",
                          "age": 10
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldUpdateUser() throws Exception {
        UserDto user = new UserDto(1L, "TestOne", "test1@test.com", 36, LocalDateTime.now());
        when(userService.update(eq(1L), any())).thenReturn(user);

        mockMvc.perform(put("/api/users/1").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                          "name": "TestOne",
                          "email": "test1@test.com",
                          "age": 36
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestOne"))
                .andExpect(jsonPath("$.email").value("test1@test.com"))
                .andExpect(jsonPath("$.age").value(36));
    }

    @Test
    void update_shouldReturn404OnNonExistentUser() throws Exception {
        when(userService.update(eq(99L), any())).thenThrow(new UserNotFoundException("Пользователь не найден"));

        mockMvc.perform(put("/api/users/99").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                          "name": "TestOne",
                          "email": "test1@test.com",
                          "age": 36
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404OnDeleteNonExistentUser() throws Exception {
        doThrow(new UserNotFoundException("Пользователь c ID 99 не найден")).when(userService).delete(99L);

        mockMvc.perform(delete("/api/users/99"))
                .andExpect(status().isNotFound());
    }
}
