package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.model.PurchaseLine;
import com.gamesUP.gamesUP.service.PurchaseLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-lines")
@RequiredArgsConstructor
public class PurchaseLineController {

    private final PurchaseLineService purchaseLineService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseLine> findAll() {
        return purchaseLineService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseLine findById(@PathVariable Long id) {
        return purchaseLineService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody PurchaseLine line) {
        return purchaseLineService.create(line);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseLine update(@PathVariable Long id, @Valid @RequestBody PurchaseLine line) {
        return purchaseLineService.update(id, line);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        purchaseLineService.delete(id);
    }
}
