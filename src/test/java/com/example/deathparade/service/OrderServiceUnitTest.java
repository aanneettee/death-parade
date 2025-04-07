package com.example.deathparade.service;

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
import com.example.deathparade.services.OrderService;
import com.example.deathparade.services.mappers.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CoffinRepository coffinRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderCache orderCache;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders_ShouldReturnListOfOrders() {
        Order order = new Order();
        OrderResponseDto dto = new OrderResponseDto();
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.toResponseDto(order)).thenReturn(dto);

        List<OrderResponseDto> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(orderRepository).findAll();
    }

    @Test
    void getOrderById_WhenOrderExists_ShouldReturnOrder() {
        Long id = 1L;
        Order order = new Order();
        OrderResponseDto dto = new OrderResponseDto();

        when(orderCache.get(id)).thenReturn(null);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderMapper.toResponseDto(order)).thenReturn(dto);

        OrderResponseDto result = orderService.getOrderById(id);

        assertEquals(dto, result);
        verify(orderRepository).findById(id);
        verify(orderCache).put(id, order);
    }

    @Test
    void getOrderById_WhenOrderCached_ShouldReturnCachedOrder() {
        Long id = 1L;
        Order order = new Order();
        OrderResponseDto dto = new OrderResponseDto();

        when(orderCache.get(id)).thenReturn(order);
        when(orderMapper.toResponseDto(order)).thenReturn(dto);

        OrderResponseDto result = orderService.getOrderById(id);

        assertEquals(dto, result);
        verify(orderRepository, never()).findById(id);
    }

    @Test
    void getOrderById_WhenOrderNotExists_ShouldThrowException() {
        Long id = 1L;

        when(orderCache.get(id)).thenReturn(null);
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.getOrderById(id));

        assertEquals(ErrorMessages.ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void createOrder_ShouldSaveAndReturnOrder() {
        OrderRequestDto requestDto = new OrderRequestDto();
        User user = new User();
        List<Coffin> coffins = List.of(new Coffin());
        Order order = new Order();
        OrderResponseDto responseDto = new OrderResponseDto();

        when(userRepository.findById(requestDto.getUserId())).thenReturn(Optional.of(user));
        when(coffinRepository.findAllById(requestDto.getCoffinIds())).thenReturn(coffins);
        when(orderMapper.toEntity(user, coffins)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toResponseDto(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.createOrder(requestDto);

        assertEquals(responseDto, result);
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrder_WhenOrderExists_ShouldUpdateAndReturnOrder() {
        Long id = 1L;
        OrderRequestDto requestDto = new OrderRequestDto();
        User user = new User();
        List<Coffin> coffins = List.of(new Coffin());
        Order order = new Order();
        OrderResponseDto responseDto = new OrderResponseDto();

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(userRepository.findById(requestDto.getUserId())).thenReturn(Optional.of(user));
        when(coffinRepository.findAllById(requestDto.getCoffinIds())).thenReturn(coffins);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toResponseDto(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.updateOrder(id, requestDto);

        assertEquals(responseDto, result);
        verify(orderRepository).save(order);
        verify(orderCache).put(id, order);
    }

    @Test
    void deleteOrder_WhenOrderExists_ShouldDeleteOrder() {
        Long id = 1L;

        when(orderRepository.existsById(id)).thenReturn(true);

        orderService.deleteOrder(id);

        verify(orderRepository).deleteById(id);
        verify(orderCache).remove(id);
    }

    @Test
    void deleteOrder_WhenOrderNotExists_ShouldThrowException() {
        Long id = 1L;

        when(orderRepository.existsById(id)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.deleteOrder(id));

        assertEquals(ErrorMessages.ORDER_NOT_FOUND, exception.getMessage());
    }
}

