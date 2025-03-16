package com.example.deathparade.services.mappers;

import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.dto.request.CoffinRequestDto;
import com.example.deathparade.models.dto.response.CoffinResponseDto;
import org.springframework.stereotype.Component;

/**.
 *
 */

@Component
public class CoffinMapper {

  /**.
   *
   */

  public Coffin toEntity(CoffinRequestDto dto) {
    Coffin coffin = new Coffin();
    coffin.setCost(dto.getCost());
    coffin.setOutsideColor(dto.getOutsideColor());
    coffin.setInsideColor(dto.getInsideColor());
    coffin.setOutsideMaterial(dto.getOutsideMaterial());
    coffin.setInsideMaterial(dto.getInsideMaterial());
    coffin.setParameters(dto.getParameters());
    return coffin;
  }

  /**.
   *
   */

  public CoffinResponseDto toResponseDto(Coffin coffin) {
    CoffinResponseDto dto = new CoffinResponseDto();
    dto.setId(coffin.getId());
    dto.setCost(coffin.getCost());
    dto.setOutsideColor(coffin.getOutsideColor());
    dto.setInsideColor(coffin.getInsideColor());
    dto.setOutsideMaterial(coffin.getOutsideMaterial());
    dto.setInsideMaterial(coffin.getInsideMaterial());
    dto.setParameters(coffin.getParameters());
    return dto;
  }
}
