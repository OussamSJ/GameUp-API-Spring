package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.GameDTO;
import com.gamesUP.gamesUP.model.Game;
import jakarta.transaction.Transactional;

import java.util.List;

public interface GameService {

    List<GameDTO> getGames();

    GameDTO getGameById(Long id);


    @Transactional
    Long create(GameDTO gameDTO);

    GameDTO update(Long id, GameDTO gameDTO);

    void delete(Long id);
}
