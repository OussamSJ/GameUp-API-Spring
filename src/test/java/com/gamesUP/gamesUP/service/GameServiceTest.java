package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.GameDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.*;
import com.gamesUP.gamesUP.repository.*;
import com.gamesUP.gamesUP.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    @Mock private GameRepository gameRepository;
    @Mock private AuthorRepository authorRepository;
    @Mock private PublisherRepository publisherRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private WishlistRepository wishlistRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGames_shouldReturnDTOList() {
        Author author = new Author(1L, "Author", null);
        Publisher publisher = new Publisher(1L, "Pub", null);
        Category category = new Category(1L, "Cat", null);

        Game game = new Game(1L, "Nom", author, "genre", category, publisher, 2);
        when(gameRepository.findAll()).thenReturn(List.of(game));

        List<GameDTO> result = gameService.getGames();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Nom");
        assertThat(result.get(0).getAuthorName()).isEqualTo("Author");

        verify(gameRepository).findAll();
    }

    @Test
    void getGameById_shouldReturnDTO() {
        Game game = new Game(1L, "Nom", new Author(), "genre", new Category(), new Publisher(), 2);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        GameDTO dto = gameService.getGameById(1L);

        assertThat(dto.getGameId()).isEqualTo(1L);

        verify(gameRepository).findById(1l);
    }

    @Test
    void getGameById_notFound_shouldThrow() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityDontExistException.class, () -> gameService.getGameById(1L));

        verify(gameRepository).findById(1l);
    }

    @Test
    void create_shouldReturnNewId() {
        GameDTO dto = new GameDTO(null, "Nom", "genre", "Author", "Publisher", "Cat", 2);
        when(authorRepository.findByName("Author")).thenReturn(Optional.of(new Author(1L, "Author", null)));
        when(publisherRepository.findByName("Publisher")).thenReturn(Optional.of(new Publisher(1L, "Publisher", null)));
        when(categoryRepository.findByType("Cat")).thenReturn(Optional.of(new Category(1L, "Cat", null)));

        Game gameToSave = new Game();
        gameToSave.setNom("Nom");

        when(gameRepository.save(any(Game.class))).thenReturn(new Game(1L, "Nom", null, "genre", null, null, 2));

        Long id = gameService.create(dto);
        assertThat(id).isEqualTo(1L);

        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void update_shouldReturnUpdatedDTO() {
        Game existing = new Game(1L, "Old", null, "OldGenre", new Category(), new Publisher(), 1);
        GameDTO updateDto = new GameDTO(null, "New", "NewGenre", null, null, null, 3);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(gameRepository.save(any())).thenReturn(existing);

        GameDTO updated = gameService.update(1L, updateDto);

        assertThat(updated.getNom()).isEqualTo("New");
        verify(gameRepository).findById(1L);
        verify(gameRepository).save(existing);
    }

    @Test
    void patch_shouldReturnPatchedDTO() {
        Game existing = new Game(1L, "Game", null, "genre", new Category(), new Publisher(), 1);
        GameDTO patchDto = new GameDTO(null, "Patched", null, null, null, null, 5);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(gameRepository.save(any())).thenReturn(existing);

        GameDTO result = gameService.patch(1L, patchDto);

        assertThat(result.getNom()).isEqualTo("Patched");
        assertThat(result.getNumEdition()).isEqualTo(5);
        verify(gameRepository).findById(1L);
        verify(gameRepository).save(existing);
    }

    @Test
    void delete_shouldRemoveGameAndWishlist() {
        Game game = new Game();
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        doNothing().when(wishlistRepository).deleteByGame(game);

        gameService.delete(1L);

        verify(gameRepository).findById(1L);
        verify(wishlistRepository).deleteByGame(game);
        verify(gameRepository).delete(game);
    }

    @Test
    void delete_whenNotExists_shouldThrow() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> gameService.delete(1L))
                .isInstanceOf(EntityDontExistException.class);
    }
    @Test
    void getGameById_shouldMapToDTOProperly() {
        Author author = new Author(1L, "Tolkien", null);
        Publisher publisher = new Publisher(1L, "Hachette", null);
        Category category = new Category(1L, "Fantasy", null);
        Game game = new Game(1L, "LOTR", author, "Adventure", category, publisher, 2);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        GameDTO dto = gameService.getGameById(1L);

        assertThat(dto.getGameId()).isEqualTo(1L);
        assertThat(dto.getNom()).isEqualTo("LOTR");
        assertThat(dto.getAuthorName()).isEqualTo("Tolkien");
        assertThat(dto.getPublisherName()).isEqualTo("Hachette");
        assertThat(dto.getCategory()).isEqualTo("Fantasy");
        assertThat(dto.getNumEdition()).isEqualTo(2);
    }

}
