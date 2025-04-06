package com.example.deathparade.models.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@AllArgsConstructor
@Setter
@Schema(description = "Standard error response format")
public class ErrorResponseDto {

  @Schema(description = "Timestamp when the error occurred", example = "2025-03-19T14:30:00")
  private LocalDateTime timestamp;

  @Schema(description = "HTTP status code", example = "400")
  private int status;

  @Schema(description = "Error message", example = "Invalid date format. Required dd-mm-yyyy")
  private String message;

  @Schema(description = "Request path where the error occurred", example = "/logs/download")
  private String path;
}
