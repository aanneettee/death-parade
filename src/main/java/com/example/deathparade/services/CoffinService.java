package com.example.deathparade.services;

import com.example.deathparade.exceptions.ErrorMessages;
import com.example.deathparade.exceptions.NotFoundException;
import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.Order;
import com.example.deathparade.models.dto.request.CoffinRequestDto;
import com.example.deathparade.models.dto.response.CoffinResponseDto;
import com.example.deathparade.repositories.CoffinRepository;
import com.example.deathparade.repositories.OrderRepository;
import com.example.deathparade.services.mappers.CoffinMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;


/**.
 *
 */

@Service
public class CoffinService {
  private final CoffinRepository coffinRepository;
  private final CoffinMapper coffinMapper;
  private final OrderRepository orderRepository;

  /**.
   *
   */

  public CoffinService(CoffinRepository coffinRepository, CoffinMapper coffinMapper,
                       OrderRepository orderRepository) {
    this.coffinRepository = coffinRepository;
    this.coffinMapper = coffinMapper;
    this.orderRepository = orderRepository;
  }

  public List<CoffinResponseDto> getAllCoffins() {
    return coffinRepository.findAll().stream()
            .map(coffinMapper::toResponseDto).toList();
  }

  /**.
   *
   */

  public CoffinResponseDto getCoffinById(Long id) {
    Coffin coffin = coffinRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorMessages.COFFIN_NOT_FOUND));
    return coffinMapper.toResponseDto(coffin);
  }

  /**.
   *
   */

  @Transactional
  public CoffinResponseDto createCoffin(CoffinRequestDto dto) {
    Coffin coffin = coffinMapper.toEntity(dto);
    return coffinMapper.toResponseDto(coffinRepository.save(coffin));
  }

  /**.
   *
   */

  @Transactional
  public CoffinResponseDto updateCoffin(Long id, CoffinRequestDto dto) {
    Coffin coffin = coffinRepository.findById(id).orElseThrow(()
            -> new NotFoundException(ErrorMessages.COFFIN_NOT_FOUND));
    coffin.setOutsideColor(dto.getOutsideColor());
    coffin.setInsideColor(dto.getInsideColor());
    coffin.setParameters(dto.getParameters());
    coffin.setCost(dto.getCost());
    coffin.setOutsideMaterial(dto.getOutsideMaterial());
    coffin.setInsideMaterial(dto.getInsideMaterial());
    return coffinMapper.toResponseDto(coffinRepository.save(coffin));
  }

  /**.
   *
   */

  @Transactional
  public void deleteCoffins(Long id) {
    Coffin coffin = coffinRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Coffin not found"));

    // Удаляем гроб из всех заказов
    for (Order order : orderRepository.findAll()) {
      if (order.getCoffins().contains(coffin)) {
        order.getCoffins().remove(coffin);
        orderRepository.save(order); // Обновляем заказ
      }
    }

    coffinRepository.deleteById(id);
  }
}
