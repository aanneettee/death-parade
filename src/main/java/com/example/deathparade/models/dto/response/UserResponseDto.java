package com.example.deathparade.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
/**.
 *
 */

@Getter
@Setter
@Schema(description = "User response DTO")
public class UserResponseDto {
  @Schema(description = "User ID", example = "1")
  private Long id;

  @Schema(description = "User's full name", example = "John Doe")
  private String name;

  @Schema(description = "User's email address", example = "john.doe@example.com")
  private String email;

  @Schema(description = "List of user's orders")
  private List<OrderResponseDto> orders = new ArrayList<>();
}
