package com.example.deathparade.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
@Schema(description = "DTO for creating order")
public class OrderRequestDto {

  @NotNull(message = "user ID is required")
  @JsonProperty(required = true)
  @Schema(
          description = "User ID",
          example = "2"
  )
  private Long userId;

  @NotNull(message = "coffin ids is required")
  @JsonProperty(required = true)
  @Schema(
          description = "Coffin IDs",
          example = "[1,2]"
  )
  private List<Long> coffinIds;

  @NotNull(message = "order data is required")
  @JsonProperty(required = true)
  @Schema(
          description = "Order data",
          example = " 2025-03-18"
  )
  private LocalDateTime orderDate;
}
