package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.service.PurchaseLineService;
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
    public List<PurchaseLineDTO> getAll() {
        return purchaseLineService.findAll();
    }

    @GetMapping("/{id}")
    public PurchaseLineDTO getById(@PathVariable Long id) {
        return purchaseLineService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody PurchaseLineDTO dto) {
        return purchaseLineService.create(dto);
    }

    @PutMapping("/{id}")
    public PurchaseLineDTO update(@PathVariable Long id, @RequestBody PurchaseLineDTO dto) {
        return purchaseLineService.update(id, dto);
    }

    @PatchMapping("/{id}")
    public PurchaseLineDTO patch(@PathVariable Long id, @RequestBody PurchaseLineDTO dto) {
        return purchaseLineService.patch(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        purchaseLineService.delete(id);
    }
}

