package com.example.user_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
  private final String field = "id";

  public UserNotFoundException(String message) {
    super(message);
  }
}
