package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.PurchaseDTO;
import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.*;
import com.gamesUP.gamesUP.repository.*;
import com.gamesUP.gamesUP.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Override
    public List<PurchaseDTO> findAll() {
        return purchaseRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PurchaseDTO findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Purchase not found"));
        return toDTO(purchase);
    }

    @Override
    public Long create(PurchaseDTO dto) {
        Purchase purchase = fromDTO(dto);
        purchase = purchaseRepository.save(purchase);
        return purchase.getId();
    }

    @Override
    public PurchaseDTO update(Long id, PurchaseDTO dto) {
        Purchase existing = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Purchase not found"));

        existing.setDate(dto.getDate());
        existing.setStatus(dto.getStatus());
        existing.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityDontExistException("User not found")));
        existing.getLines().clear();

        for (PurchaseLineDTO lineDto : dto.getLines()) {
            Game game = gameRepository.findById(lineDto.getGameId())
                    .orElseThrow(() -> new EntityDontExistException("Game not found"));
            existing.getLines().add(new PurchaseLine(null, game, existing, lineDto.getQuantite(), lineDto.getPrix()));
        }

        return toDTO(purchaseRepository.save(existing));
    }

    @Override
    public PurchaseDTO patch(Long id, PurchaseDTO dto) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Purchase not found"));

        if (dto.getDate() != null) purchase.setDate(dto.getDate());
        if (dto.getStatus() != null) purchase.setStatus(dto.getStatus());

        return toDTO(purchaseRepository.save(purchase));
    }

    @Override
    public void delete(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Purchase not found"));
        purchaseRepository.delete(purchase);
    }

    private PurchaseDTO toDTO(Purchase p) {
        List<PurchaseLineDTO> lines = p.getLines().stream().map(l ->
                new PurchaseLineDTO(
                        l.getId(),
                        p.getId(),
                        p.getUser().getId(),
                        p.getUser().getNom(),
                        l.getGame().getId(),
                        l.getGame().getNom(),
                        l.getQuantite(),
                        l.getPrix(),
                        p.getDate()
                )
        ).toList();

        return new PurchaseDTO(
                p.getId(),
                p.getUser().getId(),
                p.getUser().getNom(),
                p.getDate(),
                p.getStatus(),
                lines
        );
    }

    private Purchase fromDTO(PurchaseDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityDontExistException("User not found"));

        Purchase purchase = new Purchase();
        purchase.setDate(dto.getDate());
        purchase.setStatus(dto.getStatus());
        purchase.setUser(user);

        List<PurchaseLine> lines = dto.getLines().stream().map(line -> {
            Game game = gameRepository.findById(line.getGameId())
                    .orElseThrow(() -> new EntityDontExistException("Game not found"));
            return new PurchaseLine(null, game, purchase, line.getQuantite(), line.getPrix());
        }).toList();

        purchase.setLines(lines);
        return purchase;
    }
}
