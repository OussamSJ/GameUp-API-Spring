package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.Purchase;
import com.gamesUP.gamesUP.model.PurchaseLine;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.PurchaseLineRepository;
import com.gamesUP.gamesUP.repository.PurchaseRepository;
import com.gamesUP.gamesUP.service.PurchaseLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseLineServiceImpl implements PurchaseLineService {

    private final PurchaseLineRepository purchaseLineRepository;
    private final GameRepository gameRepository;
    private final PurchaseRepository purchaseRepository;


    @Override
    public List<PurchaseLineDTO> findAll() {
        return purchaseLineRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseLineDTO findById(Long id) {
        PurchaseLine line = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("PurchaseLine not found"));
        return mapToDTO(line);
    }

    @Override
    public Long create(PurchaseLineDTO dto) {
        Purchase purchase = purchaseRepository.findById(dto.getPurchaseId())
                .orElseThrow(() -> new EntityDontExistException("Purchase not found"));

        Game game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found"));

        PurchaseLine line = new PurchaseLine(null, game, purchase, dto.getQuantite(), dto.getPrix());
        return purchaseLineRepository.save(line).getId();
    }

    public PurchaseLineDTO update(Long id, PurchaseLineDTO dto) {
        PurchaseLine existing = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("PurchaseLine not found"));

        Game game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found"));

        existing.setGame(game);
        //existing.setPurchase(purchase); ne pas autoriser le changement de Purchase depuis les lignes
        existing.setQuantite(dto.getQuantite());
        existing.setPrix(dto.getPrix());

        return mapToDTO(purchaseLineRepository.save(existing));
    }


    @Override
    public PurchaseLineDTO patch(Long id, PurchaseLineDTO dto) {
        PurchaseLine existing = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("PurchaseLine not found"));

        if (dto.getGameId() != null) {
            Game game = gameRepository.findById(dto.getGameId())
                    .orElseThrow(() -> new EntityDontExistException("Game not found"));
            existing.setGame(game);
        }

        if (dto.getPurchaseId() != null) {
            throw  new EntityDontExistException("Cannot modify PurchaseID");


        }

        if (dto.getQuantite() != 0) existing.setQuantite(dto.getQuantite());
        if (dto.getPrix() != 0) existing.setPrix(dto.getPrix());

        return mapToDTO(purchaseLineRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        PurchaseLine line = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("PurchaseLine not found"));
        purchaseLineRepository.delete(line);
    }

    private PurchaseLineDTO mapToDTO(PurchaseLine line) {
        return new PurchaseLineDTO(
                line.getId(),
                line.getPurchase().getId(),
                line.getGame().getId(),
                line.getGame().getNom(),
                line.getQuantite(),
                line.getPrix()
        );
    }

}
