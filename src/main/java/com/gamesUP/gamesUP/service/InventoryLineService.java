package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.InventoryLine;

import java.util.List;

public interface InventoryLineService {
    List<InventoryLine> findAll();
    InventoryLine findById(Long id);
    Long create(InventoryLine inventoryLine);
    InventoryLine update(Long id, InventoryLine inventoryLine);
    void delete(Long id);
}
