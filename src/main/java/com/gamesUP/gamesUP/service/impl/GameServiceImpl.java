package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.GameDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.repository.*;
import com.gamesUP.gamesUP.service.GameService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final WishlistRepository wishlistRepository;
    @Override
    public List<GameDTO> getGames() {
        return gameRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GameDTO getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Game not found"));
        return mapToDTO(game);
    }

    @Override
    @Transactional
    public Long create(GameDTO gameDTO) {
        Game game = new Game();

        game.setNom(gameDTO.getNom());
        game.setGenre(gameDTO.getGenre());
        game.setNumEdition(gameDTO.getNumEdition());

        // Lier les entités associées
        game.setAuthor(
                authorRepository.findByName(gameDTO.getAuthorName())
                        .orElseThrow(() -> new EntityDontExistException("Author not found"))
        );
        game.setPublisher(
                publisherRepository.findByName(gameDTO.getPublisherName())
                        .orElseThrow(() -> new EntityDontExistException("Publisher not found"))
        );
        game.setCategory(
                categoryRepository.findByType(gameDTO.getCategory())
                        .orElseThrow(() -> new EntityDontExistException("Category not found"))
        );

        return gameRepository.save(game).getId();
    }


    @Override
    public GameDTO update(Long id, GameDTO gameDTO) {
        Game existing = gameRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Game not found"));

        existing.setNom(gameDTO.getNom());
        existing.setGenre(gameDTO.getGenre());
        existing.setNumEdition(gameDTO.getNumEdition());

        if (gameDTO.getAuthorName() != null) {
            existing.setAuthor(
                    authorRepository.findByName(gameDTO.getAuthorName())
                            .orElseThrow(() -> new EntityDontExistException("Author not found"))
            );
        }

        if (gameDTO.getPublisherName() != null) {
            existing.setPublisher(
                    publisherRepository.findByName(gameDTO.getPublisherName())
                            .orElseThrow(() -> new EntityDontExistException("Publisher not found"))
            );
        }

        if (gameDTO.getCategory() != null) {
            existing.setCategory(
                    categoryRepository.findByType(gameDTO.getCategory())
                            .orElseThrow(() -> new EntityDontExistException("Category not found"))
            );
        }

        Game saved = gameRepository.save(existing);
        return mapToDTO(saved);
    }
    @Override
    @Transactional
    public GameDTO patch(Long id, GameDTO partialDto) {
        Game existing = gameRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Game not found"));

        if (partialDto.getNom() != null) {
            existing.setNom(partialDto.getNom());
        }

        if (partialDto.getGenre() != null) {
            existing.setGenre(partialDto.getGenre());
        }

        if (partialDto.getNumEdition() != 0) {
            existing.setNumEdition(partialDto.getNumEdition());
        }

        if (partialDto.getAuthorName() != null) {
            existing.setAuthor(
                    authorRepository.findByName(partialDto.getAuthorName())
                            .orElseThrow(() -> new EntityDontExistException("Author not found"))
            );
        }

        if (partialDto.getPublisherName() != null) {
            existing.setPublisher(
                    publisherRepository.findByName(partialDto.getPublisherName())
                            .orElseThrow(() -> new EntityDontExistException("Publisher not found"))
            );
        }

        if (partialDto.getCategory() != null) {
            existing.setCategory(
                    categoryRepository.findByType(partialDto.getCategory())
                            .orElseThrow(() -> new EntityDontExistException("Category not found"))
            );
        }

        Game saved = gameRepository.save(existing);
        return mapToDTO(saved);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Game existing = gameRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Game not found"));
        wishlistRepository.deleteByGame(existing);
        gameRepository.delete(existing);
    }

    private GameDTO mapToDTO(Game game) {
        return new GameDTO(
                game.getId(),
                game.getNom(),
                game.getGenre(),
                game.getAuthor() != null ? game.getAuthor().getName() : null,
                game.getPublisher() != null ? game.getPublisher().getName() : null,
                game.getCategory() != null ? game.getCategory().getType() : null,
                game.getNumEdition()
        );
    }

}
