package com.example.deathparade.services.mappers;

import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.Order;
import com.example.deathparade.models.User;
import com.example.deathparade.models.dto.request.OrderRequestDto;
import com.example.deathparade.models.dto.response.OrderResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


/**.
 *
 */

@Component
public class OrderMapper {

  private final CoffinMapper coffinMapper;

  /**.
   *
   */

  public OrderMapper(CoffinMapper coffinMapper) {
    this.coffinMapper = coffinMapper;
  }

  /**.
   *
   */

  public Order toEntity(OrderRequestDto dto, User user, List<Coffin> coffins) {
    Order order = new Order();
    order.setOrderDate(java.time.LocalDateTime.now());
    order.setUser(user);
    order.setCoffins(coffins);
    return order;
  }

  /**.
   *
   */

  public OrderResponseDto toResponseDto(Order order) {
    return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getUser().getId(),
                order.getCoffins().stream()
                        .map(coffinMapper::toResponseDto)
                        .collect(Collectors.toList())
    );
  }
}
