package exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
  private String field = "id";
  private String message;

  public UserNotFoundException(String message) {
    super(message);
  }
}
