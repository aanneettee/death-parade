package com.example.deathparade.cache;

import com.example.deathparade.models.Order;
import org.springframework.stereotype.Component;

/**.
 *
 */

@Component
public class OrderCache extends LfuCache<Order> {
  /**.
   *
   */
  public OrderCache() {
    super(2);
  }
}
