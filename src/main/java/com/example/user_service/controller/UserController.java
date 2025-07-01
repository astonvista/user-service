package com.example.user_service.controller;

import com.example.user_service.dto.UserCreateDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    @Operation(summary = "Получить всех", description = "Получить список всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<List<UserDto>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить", description = "Получить пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<EntityModel<UserDto>> getById(@PathVariable @Parameter(description = "ID пользователя",
            example = "1") Long id) throws JsonProcessingException {
        UserDto userDto = service.getById(id);

        return ResponseEntity.ok(EntityModel.of(
                userDto,
                linkTo(methodOn(UserController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).update(id, new UserCreateDto())).withRel("update"),
                linkTo(methodOn(UserController.class).delete(id)).withRel("delete"),
                linkTo(methodOn(UserController.class).getAll()).withRel("list")));
    }

    @PostMapping
    @Operation(summary = "Создать", description = "Создать нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Новый пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Введены некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Введенный e-mail уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<EntityModel<UserDto>> create(@RequestBody @Valid UserCreateDto createDto)
            throws JsonProcessingException {
        UserDto userDto = service.create(new UserDto(createDto.getName(), createDto.getEmail(), createDto.getAge()));

        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(
                userDto,
                linkTo(methodOn(UserController.class).getById(userDto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).update(userDto.getId(), new UserCreateDto())).withRel("update"),
                linkTo(methodOn(UserController.class).delete(userDto.getId())).withRel("delete"),
                linkTo(methodOn(UserController.class).getAll()).withRel("list")));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить", description = "Обновить данные пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Введены некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
            @ApiResponse(responseCode = "409", description = "Введенный e-mail уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<EntityModel<UserDto>> update(@PathVariable @Parameter(description = "ID пользователя",
            example = "1") Long id, @RequestBody @Valid UserCreateDto createDto) throws JsonProcessingException {
        UserDto userDto = service.update(id, new UserDto(createDto.getName(), createDto.getEmail(), createDto.getAge()));

        return ResponseEntity.ok(EntityModel.of(
                userDto,
                linkTo(methodOn(UserController.class).getById(userDto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).update(userDto.getId(), new UserCreateDto())).withRel("update"),
                linkTo(methodOn(UserController.class).delete(userDto.getId())).withRel("delete"),
                linkTo(methodOn(UserController.class).getAll()).withRel("list")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить", description = "Удалить пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Данные успешно удалены"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "ID пользователя",
            example = "1") Long id) throws JsonProcessingException {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
