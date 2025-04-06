package com.example.deathparade.repositories;

import com.example.deathparade.models.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**.
 *
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  /**.
   *
   */
  @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
  List<Order> findOrderByUserId(@Param("userId") Long userId);

  /**.
   *
   */
  @Query(value = "SELECT * FROM orders WHERE user_id = :userId",
          nativeQuery = true)
  List<Order> findOrderByUserIdNative(@Param("userId") Long userId);
}
