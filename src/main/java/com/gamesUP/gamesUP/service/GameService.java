package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.GameDTO;
import com.gamesUP.gamesUP.model.Game;
import java.util.List;

public interface GameService {
    //List<Game> findAll();
    List<GameDTO> getGames();
    Game findById(Long id);
    Long create(Game game);
    Game update(Long id, Game game);
    void delete(Long id);
}
