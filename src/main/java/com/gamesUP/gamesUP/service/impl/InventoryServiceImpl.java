package com.gamesUP.gamesUP.service.impl;


import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Inventory;
import com.gamesUP.gamesUP.repository.InventoryRepository;
import com.gamesUP.gamesUP.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory findById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Inventory not found"));
    }

    @Override
    public Long create(Inventory inventory) {
        return inventoryRepository.save(inventory).getId();
    }

    @Override
    public Inventory update(Long id, Inventory inventory) {
        Inventory existing = findById(id);
        // Met à jour les champs
        existing.setStock(inventory.getStock()); // attention à bien gérer les lignes
        return inventoryRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Inventory existing = findById(id);
        inventoryRepository.delete(existing);
    }
}
