package com.example.deathparade.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
public class CoffinResponseDto {
  @JsonProperty(required = true)
  @Schema(
        description = "Coffin ID",
        example = "1"
  )
  private long id;

  @Schema(
          description = "Outside color of the coffin",
          example = "black"
  )
  private String outsideColor;

  @Schema(
          description = "Outside material of the coffin",
          example = "oak"
  )
  private String outsideMaterial;

  @Schema(
          description = "Inside color of the coffin",
          example = "white"
  )
  private String insideColor;

  @Schema(
          description = "Inside material of the coffin",
          example = "silk"
  )
  private String insideMaterial;

  @Schema(
          description = "Parameters of the coffin",
          example = "STANDARD"
  )
  private String parameters;

  @Schema(
          description = "Cost of the coffin",
          example = "235,5"
  )
  private BigDecimal cost;
}
