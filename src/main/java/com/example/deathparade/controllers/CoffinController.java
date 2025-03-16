package com.example.deathparade.controllers;

import com.example.deathparade.models.dto.request.CoffinRequestDto;
import com.example.deathparade.models.dto.response.CoffinResponseDto;
import com.example.deathparade.services.CoffinService;
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

/**.
 *
 */

@RestController
@RequestMapping ("/coffins")
public class CoffinController {
  private final CoffinService coffinService;

  /**.
   *
   */

  public CoffinController(CoffinService coffinService) {
    this.coffinService = coffinService;
  }

  @GetMapping
  public ResponseEntity<List<CoffinResponseDto>> getAllCoffins() {
    return ResponseEntity.ok(coffinService.getAllCoffins());
  }

  /**.
   *
   */

  @GetMapping("/{id}")
  public ResponseEntity<CoffinResponseDto> getCoffinById(@PathVariable Long id) {
    return ResponseEntity.ok(coffinService.getCoffinById(id));
  }

  /**.
   *
   */

  @PostMapping
  public ResponseEntity<CoffinResponseDto> createCoffin(@RequestBody CoffinRequestDto dto) {
    return ResponseEntity.ok(coffinService.createCoffin(dto));
  }

  /**.
   *
   */

  @PutMapping("/{id}")
  public ResponseEntity<CoffinResponseDto> updateCoffin(@PathVariable Long id,
                                                        @RequestBody CoffinRequestDto dto) {
    return ResponseEntity.ok(coffinService.updateCoffin(id, dto));
  }

  /**.
   *
   */

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCoffin(@PathVariable Long id) {
    coffinService.deleteCoffins(id);
    return ResponseEntity.noContent().build();
  }
}
