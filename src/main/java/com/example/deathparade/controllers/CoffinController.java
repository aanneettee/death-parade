package com.example.deathparade.controllers;


import com.example.deathparade.models.Coffin;
import com.example.deathparade.services.CoffinService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**.
 * Coffin controller
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
  /**.
   *
   */

  @GetMapping("/search")
  public List<Coffin> searchCoffins(
          @RequestParam(required = false) String outsideColor,
          @RequestParam(required = false) String outsideMaterial) {
    return coffinService.searchCoffins(outsideColor, outsideMaterial);
  }
  /**.
   *
   */

  @GetMapping("/{id}")
  public Coffin getCoffinById(@PathVariable int id) {
    return coffinService.getCoffinId(id);
  }
  /**.
   *
   */

  @PostMapping("/add")
  public void addCoffin(@RequestBody Coffin coffin) {
    coffinService.addCoffin(coffin);
  }
}
