package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.dto.PurchaseDTO;
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
    public List<PurchaseDTO> findAll() {
        return purchaseService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDTO findById(@PathVariable Long id) {
        return purchaseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid PurchaseDTO dto) {
        return purchaseService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDTO update(@PathVariable Long id, @RequestBody @Valid PurchaseDTO dto) {
        return purchaseService.update(id, dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDTO patch(@PathVariable Long id, @RequestBody PurchaseDTO dto) {
        return purchaseService.patch(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        purchaseService.delete(id);
    }
}
