package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.model.Purchase;
import com.gamesUP.gamesUP.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> findAll() {
        return purchaseService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Purchase findById(@PathVariable Long id) {
        return purchaseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody Purchase purchase) {
        return purchaseService.create(purchase);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Purchase update(@PathVariable Long id, @Valid @RequestBody Purchase purchase) {
        return purchaseService.update(id, purchase);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        purchaseService.delete(id);
    }
}
