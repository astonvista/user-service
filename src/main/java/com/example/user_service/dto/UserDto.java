package com.example.user_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    private String name;

    @Email(message = "Введите корректный e-mail")
    private String email;

    @Min(value = 18, message = "Возраст не может быть меньше 18 лет")
    @Max(value = 100, message = "Возраст не может быть больше 100 лет")
    private int age;

    private LocalDateTime createdAt;
}
