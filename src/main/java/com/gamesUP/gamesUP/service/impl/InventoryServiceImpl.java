package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Inventory;
import com.gamesUP.gamesUP.repository.InventoryRepository;
import com.gamesUP.gamesUP.service.InventoryService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Inventory update(Long id, Inventory inventory) {
        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Inventory not found"));
        // Met à jour les champs
        existing.setName(inventory.getName());

        return inventoryRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Inventory existing = findById(id);
        inventoryRepository.delete(existing);
    }
}
