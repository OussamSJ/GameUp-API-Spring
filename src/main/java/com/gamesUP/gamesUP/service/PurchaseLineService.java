package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.PurchaseLine;

import java.util.List;

public interface PurchaseLineService {
    List<PurchaseLine> findAll();
    PurchaseLine findById(Long id);
    Long create(PurchaseLine line);
    PurchaseLine update(Long id, PurchaseLine line);
    void delete(Long id);
}
