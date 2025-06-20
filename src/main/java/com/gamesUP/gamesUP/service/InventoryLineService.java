package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.InventoryLineDTO;
import java.util.List;

public interface InventoryLineService {
    List<InventoryLineDTO> findAll();
    InventoryLineDTO findById(Long id);
    Long create(InventoryLineDTO dto);
    InventoryLineDTO update(Long id, InventoryLineDTO dto);
    InventoryLineDTO patch(Long id, InventoryLineDTO dto);
    void delete(Long id);
    List<InventoryLineDTO> getByInventoryId(Long inventoryId);
    List<InventoryLineDTO> getByGameId(Long gameId);
}
