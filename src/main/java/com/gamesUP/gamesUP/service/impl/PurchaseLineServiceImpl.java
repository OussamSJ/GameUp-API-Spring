package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.PurchaseLine;
import com.gamesUP.gamesUP.repository.PurchaseLineRepository;
import com.gamesUP.gamesUP.service.PurchaseLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseLineServiceImpl implements PurchaseLineService {

    private final PurchaseLineRepository purchaseLineRepository;


    @Override
    public List<PurchaseLine> findAll() {
        return purchaseLineRepository.findAll();
    }

    @Override
    public PurchaseLine findById(Long id) {
        return purchaseLineRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Purchase-Line not found"));
    }

    @Override
    public Long create(PurchaseLine line) {
        return purchaseLineRepository.save(line).getId();
    }

    @Override
    public PurchaseLine update(Long id, PurchaseLine line) {
        PurchaseLine existing = findById(id);
        // Met à jour les champs
        existing.setGame(line.getGame());
        existing.setPurchase(line.getPurchase());
        existing.setQuantite(line.getQuantite());
        existing.setPrix(line.getPrix());
        return purchaseLineRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        PurchaseLine existing = findById(id);
        purchaseLineRepository.delete(existing);
    }
}
