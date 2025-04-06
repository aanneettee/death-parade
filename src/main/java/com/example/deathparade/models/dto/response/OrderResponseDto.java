package com.example.deathparade.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for returning order details")
public class OrderResponseDto {

  @JsonProperty(required = true)
  @Schema(
      description = "Order ID",
      example = "10"
  )
  private Long id;

  @Schema(
      description = "Local date and time of order",
      example = "2025-03-18"
  )
  private LocalDateTime orderDate;

  @JsonProperty(required = true)
  @Schema(
          description = "User's ID",
          example = "1"
  )
  private Long userId;

  @Schema(
          description = "Coffin's ID in order",
          example = "[2, 3]"
  )
  private List<CoffinResponseDto> coffins = new ArrayList<>();
}
