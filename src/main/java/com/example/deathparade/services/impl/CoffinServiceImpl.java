package com.example.deathparade.services.impl;

import com.example.deathparade.exeptions.ResourceNotFoundException;
import com.example.deathparade.models.Coffin;
import com.example.deathparade.models.CoffinParameters;
import com.example.deathparade.services.CoffinService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**.
 *
 */
@Service
public class CoffinServiceImpl implements CoffinService {
  private final List<Coffin> coffins = new ArrayList<>();
  /**.
   *
   */

  public CoffinServiceImpl() {
    coffins.add(new Coffin("black", "wood",
                "red", "silk", CoffinParameters.STANDARD, 260));
    coffins.add(new Coffin("pink", "wood",
                "white", "silk", CoffinParameters.CHILD, 260));
  }

  @Override
    public List<Coffin> searchCoffins(String outsideColor, String outsideMaterial) {
    List<Coffin> result = new ArrayList<>();

    for (Coffin coffin : coffins) {
      boolean matchesOutsideColor = outsideColor == null
                    || coffin.getOutsideColor().contains(outsideColor);
      boolean matchesOutsideMaterial = outsideMaterial == null
                    || coffin.getOutsideMaterial().contains(outsideMaterial);

      if (matchesOutsideColor && matchesOutsideMaterial) {
        result.add(new Coffin(
                        coffin.getOutsideColor(),
                        coffin.getOutsideMaterial(),
                        coffin.getInsideMaterial(),
                        coffin.getInsideColor(),
                        coffin.getParameters(),
                        coffin.getCost()
        ));
      }
    }

    if (result.isEmpty()) {
      throw new ResourceNotFoundException("This model does not exist");
    }
    return result;
  }

  @Override
  public Coffin getCoffinId(int id) {
    if (id >= 0 && id < coffins.size()) {
      return coffins.get(id);
    } else {
      throw new ResourceNotFoundException("Coffin with this id is not founded");
    }
  }

  @Override
  public void addCoffin(Coffin coffin) {
    coffins.add(coffin);
  }
}
