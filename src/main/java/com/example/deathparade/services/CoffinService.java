package com.example.deathparade.services;

import com.example.deathparade.models.Coffin;
import java.util.List;

/**.
 *
 */

public interface CoffinService {
  /**.
   *
   */
  List<Coffin> searchCoffins(String outsideColor, String outsideMaterial);
  /**.
   *
   */

  Coffin getCoffinId(int id);
  /**.
   *
   */

  void addCoffin(Coffin coffin);
}
