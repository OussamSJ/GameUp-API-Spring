package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.InventoryLineDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.Inventory;
import com.gamesUP.gamesUP.model.InventoryLine;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.InventoryLineRepository;
import com.gamesUP.gamesUP.repository.InventoryRepository;
import com.gamesUP.gamesUP.service.InventoryLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryLineServiceImpl implements InventoryLineService {

    private final InventoryLineRepository lineRepo;
    private final GameRepository gameRepo;
    private final InventoryRepository inventoryRepo;


    @Override
    public List<InventoryLineDTO> findAll() {
        return lineRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public InventoryLineDTO findById(Long id) {
        return lineRepo.findById(id).map(this::toDto)
                .orElseThrow(() -> new EntityDontExistException("InventoryLine not found"));
    }

    @Override
    public Long create(InventoryLineDTO dto) {
        Inventory inventory = inventoryRepo.findById(dto.getInventoryId())
                .orElseThrow(() -> new EntityDontExistException("Inventory not found"));
        Game game = gameRepo.findById(dto.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found"));

        InventoryLine line = new InventoryLine();
        line.setInventory(inventory);
        line.setGame(game);
        line.setQuantity(dto.getQuantity());

        return lineRepo.save(line).getId();
    }

    @Override
    public InventoryLineDTO update(Long id, InventoryLineDTO dto) {
        InventoryLine line = lineRepo.findById(id)
                .orElseThrow(() -> new EntityDontExistException("InventoryLine not found"));

        Inventory inventory = inventoryRepo.findById(dto.getInventoryId())
                .orElseThrow(() -> new EntityDontExistException("Inventory not found"));
        Game game = gameRepo.findById(dto.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found"));

        line.setInventory(inventory);
        line.setGame(game);
        line.setQuantity(dto.getQuantity());

        return toDto(lineRepo.save(line));
    }

    @Override
    public InventoryLineDTO patch(Long id, InventoryLineDTO dto) {
        InventoryLine line = lineRepo.findById(id)
                .orElseThrow(() -> new EntityDontExistException("InventoryLine not found"));

        if (dto.getInventoryId() != null) {
            Inventory inventory = inventoryRepo.findById(dto.getInventoryId())
                    .orElseThrow(() -> new EntityDontExistException("Inventory not found"));
            line.setInventory(inventory);
        }

        if (dto.getGameId() != null) {
            Game game = gameRepo.findById(dto.getGameId())
                    .orElseThrow(() -> new EntityDontExistException("Game not found"));
            line.setGame(game);
        }

        if (dto.getQuantity() != 0) {
            line.setQuantity(dto.getQuantity());
        }

        return toDto(lineRepo.save(line));
    }

    @Override
    public void delete(Long id) {
        InventoryLine line = lineRepo.findById(id)
                .orElseThrow(() -> new EntityDontExistException("InventoryLine not found"));
        lineRepo.delete(line);
    }

    @Override
    public List<InventoryLineDTO> getByInventoryId(Long inventoryId) {
        return lineRepo.findByInventoryId(inventoryId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryLineDTO> getByGameId(Long gameId) {
        return lineRepo.findByGameId(gameId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private InventoryLineDTO toDto(InventoryLine line) {
        return new InventoryLineDTO(
                line.getId(),
                line.getInventory().getId(),
                line.getInventory().getName(),
                line.getGame().getId(),
                line.getGame().getNom(),
                line.getQuantity()
        );
    }

}
