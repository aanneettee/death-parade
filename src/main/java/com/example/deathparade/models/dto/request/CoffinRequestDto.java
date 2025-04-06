package com.example.deathparade.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
@Schema(description = "DTO for creating/updating coffin specifications")
public class CoffinRequestDto {

  @NotNull(message = "Outside color is required")
  @Size(min = 3, max = 50, message = "Outside color must be between 3 and 50 characters")
  @JsonProperty(required = true)
  @Schema(
          description = "Color of the coffin's exterior",
          example = "Black",
          minLength = 3,
          maxLength = 50
  )
  private String outsideColor;

  @NotNull(message = "Outside material is required")
  @Size(min = 3, max = 50, message = "Outside material must be between 3 and 50 characters")
  @JsonProperty(required = true)
  @Schema(
          description = "Material of the coffin's exterior",
          example = "Wood",
          minLength = 3,
          maxLength = 50
  )
  private String outsideMaterial;

  @NotNull(message = "Inside color is required")
  @Size(min = 3, max = 50, message = "Inside color must be between 3 and 50 characters")
  @JsonProperty(required = true)
  @Schema(
          description = "Color of the coffin's interior lining",
          example = "White",
          minLength = 3,
          maxLength = 50
  )
  private String insideColor;

  @NotNull(message = "Inside material is required")
  @Size(min = 3, max = 50, message = "Inside material must be between 3 and 50 characters")
  @JsonProperty(required = true)
  @Schema(
          description = "Material of the coffin's interior lining",
          example = "Satin",
          minLength = 3,
          maxLength = 50
  )
  private String insideMaterial;

  @Size(max = 500, message = "Parameters must be less than 500 characters")
  @JsonProperty(required = true)
  @Schema(
          description = "Additional specifications (dimensions, weight, etc.)",
          example = "STANDARD",
          maxLength = 500
  )
  private String parameters;

  @NotNull(message = "Cost is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
  @Digits(integer = 6, fraction = 2,
          message = "Cost must have up to 6 integer and 2 decimal digits")
  @JsonProperty(required = true)
  @Schema(
          description = "Price of the coffin",
          example = "999.99",
          minimum = "0.01"
  )
  private BigDecimal cost;
}
