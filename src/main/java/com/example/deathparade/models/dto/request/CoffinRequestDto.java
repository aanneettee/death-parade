package com.example.deathparade.models.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
public class CoffinRequestDto {
  private String outsideColor;
  private String outsideMaterial;
  private String insideColor;
  private String insideMaterial;
  private String parameters;
  private BigDecimal cost;
}
