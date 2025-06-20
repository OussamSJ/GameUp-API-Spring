package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.dto.InventoryLineDTO;
import com.gamesUP.gamesUP.service.InventoryLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-lines")
@RequiredArgsConstructor
public class InventoryLineController {

    private final InventoryLineService service;

    @GetMapping
    public List<InventoryLineDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public InventoryLineDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid InventoryLineDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public InventoryLineDTO update(@PathVariable Long id, @RequestBody @Valid InventoryLineDTO dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public InventoryLineDTO patch(@PathVariable Long id, @RequestBody InventoryLineDTO dto) {
        return service.patch(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @GetMapping("/inventory/{inventoryId}")
    public List<InventoryLineDTO> getByInventory(@PathVariable Long inventoryId) {
        return service.getByInventoryId(inventoryId);
    }

    @GetMapping("/game/{gameId}")
    public List<InventoryLineDTO> getByGame(@PathVariable Long gameId) {
        return service.getByGameId(gameId);
    }

}
