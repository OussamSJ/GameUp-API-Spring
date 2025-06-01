package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.Avis;

import java.util.List;

public interface AvisService {
    List<Avis> findAll();
    Avis findById(Long id);
    Avis create(Avis avis);
    Avis update(Long id, Avis avis);
    void patch(Long id, Avis partialAvis);
    void delete(Long id);
    List<Avis> getAvisByGameId(Long gameId);
    List<Avis> getAvisByUserId(Long userId);
}
