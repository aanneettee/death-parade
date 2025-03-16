package com.example.deathparade.repositories;

import com.example.deathparade.models.Coffin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**.
 *
 */
@Repository
public interface CoffinRepository extends JpaRepository<Coffin, Long> {

}
