package com.example.deathparade.models.dto.response;

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
public class OrderResponseDto {
  private Long id;
  private LocalDateTime orderDate;
  private Long userId;
  private List<CoffinResponseDto> coffins = new ArrayList<>();
}
