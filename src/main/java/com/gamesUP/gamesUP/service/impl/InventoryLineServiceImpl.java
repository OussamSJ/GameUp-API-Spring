package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.InventoryLine;
import com.gamesUP.gamesUP.repository.InventoryLineRepository;
import com.gamesUP.gamesUP.service.InventoryLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryLineServiceImpl implements InventoryLineService {

    private final InventoryLineRepository inventoryLineRepository;

    @Override
    public List<InventoryLine> findAll() {
        return inventoryLineRepository.findAll();
    }

    @Override
    public InventoryLine findById(Long id) {
        return inventoryLineRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException());
    }

    @Override
    public Long create(InventoryLine inventoryLine) {
        return inventoryLineRepository.save(inventoryLine).getId();
    }

    @Override
    public InventoryLine update(Long id, InventoryLine inventoryLine) {
        InventoryLine existing = findById(id);
        existing.setInventory(inventoryLine.getInventory());
        existing.setGame(inventoryLine.getGame());
        existing.setQuantity(inventoryLine.getQuantity());
        return inventoryLineRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        InventoryLine existing = findById(id);
        inventoryLineRepository.delete(existing);
    }
}
