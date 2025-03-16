package com.example.deathparade.models.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
public class CoffinResponseDto {
  private long id;
  private String outsideColor;
  private String outsideMaterial;
  private String insideColor;
  private String insideMaterial;
  private String parameters;
  private BigDecimal cost;
}
