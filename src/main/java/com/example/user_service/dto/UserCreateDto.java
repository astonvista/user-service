package com.example.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Пользователь")
public class UserCreateDto {

    @NotBlank(message = "Имя не может быть пустым")
    @Pattern(regexp = "^\\D*$", message = "Имя не может содержать цифры")
    @Schema(description = "Имя пользователя", example = "Name")
    private String name;

    @NotBlank(message = "E-mail не может быть пустым")
    @Email(message = "Введите корректный e-mail")
    @Schema(description = "E-mail пользователя", example = "test@test.com")
    private String email;

    @NotNull
    @Min(value = 18, message = "Возраст не может быть меньше 18 лет")
    @Max(value = 100, message = "Возраст не может быть больше 100 лет")
    @Schema(description = "Возраст пользователя", example = "18")
    private int age;
}
