package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AvisRepository extends JpaRepository<Avis,Integer> {

    List<Avis> findByGameId(Long gameId);
    List<Avis> findByUserId(Long userId);
}
