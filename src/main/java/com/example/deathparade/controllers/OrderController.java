package com.example.deathparade.controllers;

import com.example.deathparade.models.dto.request.OrderRequestDto;
import com.example.deathparade.models.dto.response.OrderResponseDto;
import com.example.deathparade.services.OrderService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Контроллер для управления заказами.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;

  /**
   *.
   */

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * .
   */

  @GetMapping
  public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
  }

  /**
   * Получить заказ по ID.
   */

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
  }

  /**
   * .
   */

  @PostMapping
  public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto dto) {
    return ResponseEntity.ok(orderService.createOrder(dto));
  }

  /**
   * .
   */

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id,
                                                      @RequestBody OrderRequestDto dto) {
    return ResponseEntity.ok(orderService.updateOrder(id, dto));
  }

  /**.
   *
   */

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
