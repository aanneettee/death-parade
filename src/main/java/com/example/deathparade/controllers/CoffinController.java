package com.example.deathparade.controllers;


import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.CoffinParameters;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**.
 * Coffin controller
 */
@RestController
@RequestMapping ("/coffins")
public class CoffinController {
  private final List<Coffin> coffins = new ArrayList<>();
  /**.
   *
   */

  CoffinController() {
    coffins.add(new Coffin("black", "wood",
            "red", "silk", CoffinParameters.STANDARD, 260));
    coffins.add(new Coffin("pink", "wood",
            "white", "silk", CoffinParameters.CHILD, 260));
  }
  /**.
   *
   */

  @GetMapping("/search")
  public List<Coffin> searchCoffins(
          @RequestParam(required = false) String outsideColor,
          @RequestParam(required = false) String outsideMaterial) {
    List<Coffin> result = new ArrayList<>();

    for (Coffin coffin : coffins) {
      boolean matchesOutsideColor = outsideColor == null
              || coffin.getOutsideColor().contains(outsideColor);
      boolean matchesOutsideMaterial = outsideMaterial == null
              || coffin.getOutsideMaterial().contains(outsideMaterial);

      if (matchesOutsideColor && matchesOutsideMaterial) {
        result.add(coffin);
      }
    }

    return result;
  }

  /**.
   *
   */

  @GetMapping("/{id}")
  public Coffin getCoffinId(@PathVariable int id) {
    if (id >= 0 && id < coffins.size()) {
      return coffins.get(id);
    } else {
      throw new IllegalArgumentException("Such a coffin was not found: " + id);
    }
  }
}
