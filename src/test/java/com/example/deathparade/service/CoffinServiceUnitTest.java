package com.example.deathparade.service;

import com.example.deathparade.exceptions.ErrorMessages;
import com.example.deathparade.exceptions.NotFoundException;
import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.Order;
import com.example.deathparade.models.dto.request.CoffinRequestDto;
import com.example.deathparade.models.dto.response.CoffinResponseDto;
import com.example.deathparade.repositories.CoffinRepository;
import com.example.deathparade.repositories.OrderRepository;
import com.example.deathparade.services.CoffinService;
import com.example.deathparade.services.mappers.CoffinMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoffinServiceUnitTest {

    @Mock
    private CoffinRepository coffinRepository;

    @Mock
    private CoffinMapper coffinMapper;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CoffinService coffinService;

    @Test
    void getAllCoffins_ShouldReturnListOfCoffins() {
        // Arrange
        Coffin coffin = new Coffin();
        CoffinResponseDto dto = new CoffinResponseDto();
        when(coffinRepository.findAll()).thenReturn(List.of(coffin));
        when(coffinMapper.toResponseDto(coffin)).thenReturn(dto);

        // Act
        List<CoffinResponseDto> result = coffinService.getAllCoffins();

        // Assert
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(coffinRepository).findAll();
    }

    @Test
    void getCoffinById_WhenCoffinExists_ShouldReturnCoffin() {
        // Arrange
        Long id = 1L;
        Coffin coffin = new Coffin();
        CoffinResponseDto dto = new CoffinResponseDto();

        when(coffinRepository.findById(id)).thenReturn(Optional.of(coffin));
        when(coffinMapper.toResponseDto(coffin)).thenReturn(dto);

        // Act
        CoffinResponseDto result = coffinService.getCoffinById(id);

        // Assert
        assertEquals(dto, result);
        verify(coffinRepository).findById(id);
    }

    @Test
    void getCoffinById_WhenCoffinNotExists_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        when(coffinRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> coffinService.getCoffinById(id));

        assertEquals(ErrorMessages.COFFIN_NOT_FOUND, exception.getMessage());
    }

    @Test
    void createCoffin_ShouldSaveAndReturnCoffin() {
        // Arrange
        CoffinRequestDto requestDto = new CoffinRequestDto();
        Coffin coffin = new Coffin();
        Coffin savedCoffin = new Coffin();
        CoffinResponseDto responseDto = new CoffinResponseDto();

        when(coffinMapper.toEntity(requestDto)).thenReturn(coffin);
        when(coffinRepository.save(coffin)).thenReturn(savedCoffin);
        when(coffinMapper.toResponseDto(savedCoffin)).thenReturn(responseDto);

        // Act
        CoffinResponseDto result = coffinService.createCoffin(requestDto);

        // Assert
        assertEquals(responseDto, result);
        verify(coffinRepository).save(coffin);
    }

    @Test
    void updateCoffin_WhenCoffinExists_ShouldUpdateAndReturnCoffin() {
        // Arrange
        Long id = 1L;
        CoffinRequestDto requestDto = new CoffinRequestDto();
        requestDto.setOutsideColor("Black");
        requestDto.setInsideColor("White");
        requestDto.setParameters("200x80x60");
        requestDto.setCost(new BigDecimal("999.99"));
        requestDto.setOutsideMaterial("Wood");
        requestDto.setInsideMaterial("Silk");

        Coffin coffin = new Coffin();
        CoffinResponseDto responseDto = new CoffinResponseDto();

        when(coffinRepository.findById(id)).thenReturn(Optional.of(coffin));
        when(coffinRepository.save(coffin)).thenReturn(coffin);
        when(coffinMapper.toResponseDto(coffin)).thenReturn(responseDto);

        // Act
        CoffinResponseDto result = coffinService.updateCoffin(id, requestDto);

        // Assert
        assertEquals("Black", coffin.getOutsideColor());
        assertEquals("White", coffin.getInsideColor());
        assertEquals("200x80x60", coffin.getParameters());
        assertEquals(0, new BigDecimal("999.99").compareTo(coffin.getCost()));
        assertEquals("Wood", coffin.getOutsideMaterial());
        assertEquals("Silk", coffin.getInsideMaterial());
        assertEquals(responseDto, result);
        verify(coffinRepository).save(coffin);
    }

    @Test
    void deleteCoffins_WhenCoffinExists_ShouldRemoveFromOrdersAndDelete() {
        // Arrange
        Long id = 1L;
        Coffin coffin = new Coffin();
        Order order = new Order();
        order.setCoffins(Collections.singletonList(coffin));

        when(coffinRepository.findById(id)).thenReturn(Optional.of(coffin));
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        // Act
        coffinService.deleteCoffins(id);

        // Assert
        verify(orderRepository).save(order);
        verify(coffinRepository).deleteById(id);
        assertTrue(order.getCoffins().isEmpty());
    }

    @Test
    void deleteCoffins_WhenCoffinNotExists_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        when(coffinRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> coffinService.deleteCoffins(id));

        assertEquals("Coffin not found", exception.getMessage());
    }
}
