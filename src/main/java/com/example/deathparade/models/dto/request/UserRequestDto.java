package com.example.deathparade.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
@Schema(description = "User creation request DTO")
public class UserRequestDto {
  @NotNull(message = "Name is required")
  @Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters")
  @Schema(
          description = "User's full name",
          example = "John Doe",
          minLength = 3,
          maxLength = 25
  )
  @JsonProperty(required = true)
  private String name;

  @NotNull(message = "Email is required")
  @Pattern(
          regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,5}$",
          message = "Invalid email format. Expected format: example@domain.com"
  )
  @Schema(
          description = "User's email address",
          example = "john.doe@example.com",
          pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,5}$"
  )
  @JsonProperty(required = true)
  private String email;
}
