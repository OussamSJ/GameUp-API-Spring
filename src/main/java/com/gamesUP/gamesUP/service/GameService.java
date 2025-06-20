package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.GameDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface GameService {

    List<GameDTO> getGames();

    GameDTO getGameById(Long id);


    @Transactional
    Long create(GameDTO gameDTO);

    GameDTO update(Long id, GameDTO gameDTO);

    @Transactional
    GameDTO patch(Long id, GameDTO partialDto);

    void delete(Long id);
}
