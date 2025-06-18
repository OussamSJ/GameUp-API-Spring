package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Purchase;
import com.gamesUP.gamesUP.model.PurchaseLine;
import com.gamesUP.gamesUP.repository.PurchaseRepository;
import com.gamesUP.gamesUP.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase findById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException());
    }

    @Override
    public Long create(Purchase purchase) {
        return purchaseRepository.save(purchase).getId();
    }

    @Override
    public Purchase update(Long id, Purchase updatedPurchase) {
        Purchase existing = findById(id);
        // Met à jour les champs
        existing.setDate(updatedPurchase.getDate());
        existing.setStatus(updatedPurchase.getStatus());

        return purchaseRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Purchase existing = findById(id);
        purchaseRepository.delete(existing);
    }
}
