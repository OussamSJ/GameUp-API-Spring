package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> findAll();
    Inventory findById(Long id);
    Long create(Inventory inventory);
    Inventory update(Long id, Inventory inventory);
    void delete(Long id);
}
