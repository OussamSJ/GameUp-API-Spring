package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.GameDTO;
import com.gamesUP.gamesUP.dto.WishlistGameDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.Wishlist;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    /*@Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }*/
    public List<GameDTO> getGames() {
        List<Game> items = gameRepository.findAll();

        return items.stream()
                .map(item -> {
                    Game game = item;
                    return new GameDTO(
                            game.getId(),
                            game.getNom(),
                            game.getGenre(),
                            game.getAuthor() != null ? game.getAuthor().getName() : null,
                            game.getPublisher() != null ? game.getPublisher().getName() : null,
                            game.getCategory() != null ? game.getCategory().getType() : null,
                            game.getNumEdition()
                            // dateAdded à ajouter si tu modifies l'entité Wishlist pour l'inclure
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Game not found"));
    }

    @Override
    public Long create(Game game) {
        return gameRepository.save(game).getId();
    }

    @Override
    public Game update(Long id, Game game) {
        Game existing = findById(id);
        existing.setNom(game.getNom());
        existing.setGenre(game.getGenre());
        existing.setCategory(game.getCategory());
        existing.setPublisher(game.getPublisher());
        existing.setAuthor(game.getAuthor());
        existing.setNumEdition(game.getNumEdition());
        return gameRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Game existing = findById(id);
        gameRepository.delete(existing);
    }
}
