package com.example.deathparade.services;

import com.example.deathparade.exceptions.EntityNotFoundException;
import com.example.deathparade.exceptions.ErrorMessages;
import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.Order;
import com.example.deathparade.models.User;
import com.example.deathparade.models.dto.request.OrderRequestDto;
import com.example.deathparade.models.dto.response.OrderResponseDto;
import com.example.deathparade.repositories.CoffinRepository;
import com.example.deathparade.repositories.OrderRepository;
import com.example.deathparade.repositories.UserRepository;
import com.example.deathparade.services.mappers.OrderMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления заказами.
 */
@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final CoffinRepository coffinRepository;
  private final OrderMapper orderMapper;

  /**
   * Конструктор сервиса заказов.
   */
  public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        CoffinRepository coffinRepository, OrderMapper orderMapper) {
    this.orderRepository = orderRepository;
    this.userRepository = userRepository;
    this.coffinRepository = coffinRepository;
    this.orderMapper = orderMapper;
  }

  /**.
   *
   */

  public List<OrderResponseDto> getAllOrders() {
    return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDto)
                .toList();
  }

  /**.
   *
   */

  public OrderResponseDto getOrderById(Long id) {
    Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.ORDER_NOT_FOUND));
    return orderMapper.toResponseDto(order);
  }

  /**.
   *
   */

  @Transactional
  public OrderResponseDto createOrder(OrderRequestDto dto) {
    User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));

    List<Coffin> coffins = coffinRepository.findAllById(dto.getCoffinIds());

    if (coffins.isEmpty()) {
      throw new EntityNotFoundException(ErrorMessages.COFFIN_NOT_FOUND);
    }

    Order order = orderMapper.toEntity(dto, user, coffins);
    return orderMapper.toResponseDto(orderRepository.save(order));
  }

  /**.
   *
   */

  @Transactional
  public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
    Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.ORDER_NOT_FOUND));

    User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));

    List<Coffin> coffins = coffinRepository.findAllById(dto.getCoffinIds());

    if (coffins.isEmpty()) {
      throw new EntityNotFoundException(ErrorMessages.COFFIN_NOT_FOUND);
    }

    order.setUser(user);
    order.setCoffins(coffins);

    return orderMapper.toResponseDto(orderRepository.save(order));
  }

  /**.
   *
   */

  @Transactional
  public void deleteOrder(Long id) {
    if (!orderRepository.existsById(id)) {
      throw new EntityNotFoundException(ErrorMessages.ORDER_NOT_FOUND);
    }
    orderRepository.deleteById(id);
  }
}
