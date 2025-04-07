package com.example.deathparade.services;

import com.example.deathparade.cache.OrderCache;
import com.example.deathparade.exceptions.ErrorMessages;
import com.example.deathparade.exceptions.NotFoundException;
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

/**.
 *
 */

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final CoffinRepository coffinRepository;
  private final OrderMapper orderMapper;
  private final OrderCache orderCache;
  /**
  * Конструктор сервиса заказов.
  */

  public OrderService(OrderRepository orderRepository,
                      UserRepository userRepository,
                      CoffinRepository coffinRepository,
                      OrderMapper orderMapper,
                      OrderCache orderCache) {
    this.orderRepository = orderRepository;
    this.coffinRepository = coffinRepository;
    this.orderMapper = orderMapper;
    this.userRepository = userRepository;
    this.orderCache = orderCache;
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
    Order cachedOrder = orderCache.get(id);
    if (cachedOrder != null) {
      return orderMapper.toResponseDto(cachedOrder);
    }

    Order order = orderRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorMessages.ORDER_NOT_FOUND));
    orderCache.put(id, order);
    return orderMapper.toResponseDto(order);
  }

  /**.
   *
   */

  public List<OrderResponseDto> findOrderByUserId(Long userId) {

    if (!userRepository.existsById(userId)) {
      throw new NotFoundException(ErrorMessages.USER_NOT_FOUND);
    }

    return orderRepository.findOrderByUserId(userId).stream().map(orderMapper::toResponseDto)
            .toList();
  }

  /**.
   *
   */

  public List<OrderResponseDto> findOrderByUserIdNative(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException(ErrorMessages.USER_NOT_FOUND);
    }

    return orderRepository.findOrderByUserIdNative(userId).stream().map(orderMapper::toResponseDto)
            .toList();
  }
  /**.
   *
   */

  @Transactional
  public OrderResponseDto createOrder(OrderRequestDto dto) {
    User user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
      new NotFoundException(ErrorMessages.USER_NOT_FOUND));

    List<Coffin> coffins = coffinRepository.findAllById(dto.getCoffinIds());
    Order order = orderMapper.toEntity(user, coffins);
    return orderMapper.toResponseDto(orderRepository.save(order));
  }

  /**.
   *
   */

  @Transactional
  public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
    Order order = orderRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorMessages.ORDER_NOT_FOUND));

    if (dto.getUserId() != null) {
      User user = userRepository.findById(dto.getUserId())
              .orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
      order.setUser(user);
    }

    if (dto.getCoffinIds() != null && !dto.getCoffinIds().isEmpty()) {
      List<Coffin> coffins = coffinRepository.findAllById(dto.getCoffinIds());
      if (coffins.size() != dto.getCoffinIds().size()) {
        throw new NotFoundException(ErrorMessages.COFFIN_NOT_FOUND);
      }
      order.setCoffins(coffins);
    }
    Order savedOrder = orderRepository.save(order);
    orderCache.put(savedOrder.getId(), savedOrder);

    return orderMapper.toResponseDto(savedOrder);
  }

  /**.
   *
   */

  @Transactional
  public void deleteOrder(Long id) {
    if (!orderRepository.existsById(id)) {
      throw new NotFoundException(ErrorMessages.ORDER_NOT_FOUND);
    }
    orderRepository.deleteById(id);
    orderCache.remove(id);
  }
}