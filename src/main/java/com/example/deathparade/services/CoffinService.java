package com.example.deathparade.services;

import com.example.deathparade.exceptions.EntityNotFoundException;
import com.example.deathparade.exceptions.ErrorMessages;
import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.dto.request.CoffinRequestDto;
import com.example.deathparade.models.dto.response.CoffinResponseDto;
import com.example.deathparade.repositories.CoffinRepository;
import com.example.deathparade.services.mappers.CoffinMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;


/**.
 *
 */
@Service
public class CoffinService {
  private final CoffinRepository coffinRepository;
  private final CoffinMapper coffinMapper;

  public CoffinService(CoffinRepository coffinRepository, CoffinMapper coffinMapper) {
    this.coffinRepository = coffinRepository;
    this.coffinMapper = coffinMapper;
  }

  public List<CoffinResponseDto> getAllCoffins() {
    return coffinRepository.findAll().stream()
            .map(coffinMapper::toResponseDto).toList();
  }

  public CoffinResponseDto getCoffinById(Long id) {
    Coffin coffin = coffinRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.COFFIN_NOT_FOUND));
  return coffinMapper.toResponseDto(coffin);
  }

  @Transactional
  public CoffinResponseDto createCoffin(CoffinRequestDto dto) {
    Coffin coffin = coffinMapper.toEntity(dto);
    return coffinMapper.toResponseDto(coffinRepository.save(coffin));
  }

  @Transactional
  public CoffinResponseDto updateCoffin(Long id, CoffinRequestDto dto) {
    Coffin coffin = coffinRepository.findById(id).orElseThrow(()
            -> new EntityNotFoundException(ErrorMessages.COFFIN_NOT_FOUND));
    coffin.setOutsideColor(dto.getOutsideColor());
    coffin.setInsideColor(dto.getInsideColor());
    coffin.setParameters(dto.getParameters());
    coffin.setCost(dto.getCost());
    coffin.setOutsideMaterial(dto.getOutsideMaterial());
    coffin.setInsideMaterial(dto.getInsideMaterial());
    return coffinMapper.toResponseDto(coffinRepository.save(coffin));
  }

  @Transactional
  public void deleteCoffins(Long id){
    coffinRepository.deleteById(id);
  }
}
