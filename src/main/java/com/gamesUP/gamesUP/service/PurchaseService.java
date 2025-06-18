package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.Purchase;
import java.util.List;

public interface PurchaseService {
    List<Purchase> findAll();
    Purchase findById(Long id);
    Long create(Purchase purchase);
    Purchase update(Long id, Purchase purchase);
    void delete(Long id);
}
