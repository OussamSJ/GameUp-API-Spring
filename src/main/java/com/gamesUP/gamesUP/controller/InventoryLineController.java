package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.model.InventoryLine;
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

    private final InventoryLineService inventoryLineService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryLine> findAll() {
        return inventoryLineService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryLine findById(@PathVariable Long id) {
        return inventoryLineService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody InventoryLine inventoryLine) {
        return inventoryLineService.create(inventoryLine);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryLine update(@PathVariable Long id, @Valid @RequestBody InventoryLine inventoryLine) {
        return inventoryLineService.update(id, inventoryLine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inventoryLineService.delete(id);
    }
}
