package com.example.deathparade.models.dto.request;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**.
 *
 */

@Getter
@Setter
public class OrderRequestDto {
  private Long userId;
  private List<Long> coffinIds;
}
