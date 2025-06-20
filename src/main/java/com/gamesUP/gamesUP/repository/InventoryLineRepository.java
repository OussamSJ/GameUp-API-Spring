package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.InventoryLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryLineRepository extends JpaRepository<InventoryLine, Long> {
    List<InventoryLine> findByInventoryId(Long inventoryId);
    List<InventoryLine> findByGameId(Long gameId);

}
