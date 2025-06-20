package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.PurchaseDTO;
import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.model.Purchase;

import java.util.List;
public interface PurchaseService {
    List<PurchaseDTO> findAll();
    PurchaseDTO findById(Long id);
    Long create(PurchaseDTO purchaseDTO);
    PurchaseDTO update(Long id, PurchaseDTO purchaseDTO);
    PurchaseDTO patch(Long id, PurchaseDTO purchaseDTO);
    void delete(Long id);
}

