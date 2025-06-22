package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.AvisDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Avis;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.User;
import com.gamesUP.gamesUP.repository.AvisRepository;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.UserRepository;
import com.gamesUP.gamesUP.service.impl.AvisServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AvisServiceTest {

        @Mock
        private AvisRepository avisRepository;
        @Mock
        private GameRepository gameRepository;
        @Mock
        private UserRepository userRepository;
        @InjectMocks
        private AvisServiceImpl avisService;

        @BeforeEach
        void setup() {
            MockitoAnnotations.openMocks(this);
        }


        @Test
    void getAll_shouldReturnListOfAvisDTOs() {
        User user = new User(1L, "Alice", null);
        Game game = new Game();
        game.setId(1L);
        game.setNom("Zelda");
        Avis avis = new Avis(1L, "Super jeu", 5, user, game);

        when(avisRepository.findAll()).thenReturn(List.of(avis));

        List<AvisDTO> result = avisService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCommentaire()).isEqualTo("Super jeu");
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        assertThat(result.get(0).getGameNom()).isEqualTo("Zelda");
        verify(avisRepository).findAll();
    }


    @Test
    void getById_shouldReturnAvisDTO() {
        User user = new User(1L, "Alice", null);
        Game game = new Game();
        game.setId(1L);
        game.setNom("Zelda");

        Avis avis = new Avis(1L, "Comment", 4, user, game);

        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));

        AvisDTO dto = avisService.getById(1L);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getUserId()).isEqualTo(1L);
        assertThat(dto.getGameId()).isEqualTo(1L);
    }
    @Test
    void getById_shouldThrowIfNotFound() {
        when(avisRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityDontExistException.class, () -> avisService.getById(1L));
    }

    @Test
    void create_shouldReturnId() {
        User user = new User(1L, "Bob", null);
        Game game = new Game();
        game.setId(2L);
        game.setNom("Mario");
        Avis avis = new Avis(null, "Cool", 5, user, game);
        Avis saved = new Avis(1L, "Cool", 5, user, game);

        when(gameRepository.findById(2L)).thenReturn(Optional.of(game));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(avisRepository.save(any(Avis.class))).thenReturn(saved);

        AvisDTO dto = new AvisDTO(null, "Cool", 5, 1L, "Bob", 2L, "Mario");
        Long id = avisService.create(dto);

        verify(gameRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(avisRepository).save(avis);
        assertThat(id).isEqualTo(1L);

    }
    @Test
    void update_shouldUpdateAvisAndReturnDto() {
        User user = new User(1L, "Alice", null);
        Game game = new Game();
        game.setId(1L);
        game.setNom("Zelda");
        game.setNumEdition(1);
        Avis existing = new Avis(1L, "Old Comment", 3, user, game);

        AvisDTO updateDto = new AvisDTO(1L, "New Comment", 4, 1L, "Alice", 1L, "Zelda");

        when(avisRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(avisRepository.save(any(Avis.class))).thenReturn(existing);

        AvisDTO result = avisService.update(1L, updateDto);

        assertThat(result.getCommentaire()).isEqualTo("New Comment");
        assertThat(result.getNote()).isEqualTo(4);
        verify(avisRepository).findById(1L);
        verify(avisRepository).save(existing);
    }


    @Test
    void patch_shouldUpdateFields() {
        User user = new User(1L, "Eve", null);
        Game game = new Game();
        game.setId(3L);
        game.setNom("Sonic");

        Avis avis = new Avis(1L, "Old", 2, user, game);

        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));
        when(avisRepository.save(any())).thenReturn(avis);

        AvisDTO dto = new AvisDTO(null, "Updated", 5, 2L, null, null, null);

        AvisDTO updated = avisService.patch(1L, dto);

        assertThat(updated.getCommentaire()).isEqualTo("Updated");
        assertThat(updated.getNote()).isEqualTo(5);

    }

    @Test
    void delete_shouldRemoveAvis() {
        User user = new User(1L, "David", null);
        Game game = new Game();
        game.setId(2L);
        game.setNom("Halo");

        Avis avis = new Avis(1L, "Pas mal", 3, user, game);
        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));
        doNothing().when(avisRepository).delete(avis);

        avisService.delete(1L);

        verify(avisRepository).findById(1L);
        verify(avisRepository).delete(avis);
    }

    @Test
    void getAvisByUser_shouldReturnListOfAvisDTOs() {
        User user = new User(3L, "Bob", null);
        Game game = new Game();
        game.setId(2L);
        game.setNom("Halo");
        Avis avis = new Avis(1L, "Addictif !", 4, user, game);

        when(avisRepository.findByUserId(3L)).thenReturn(List.of(avis));

        List<AvisDTO> result = avisService.getAvisByUser(3L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getUserId()).isEqualTo(3L);
        assertThat(result.get(0).getUserNom()).isEqualTo("Bob");

        verify(avisRepository).findByUserId(3L);
    }

    @Test
    void getAvisByGame_shouldReturnListOfAvisDTOs() {
        User user = new User(1L, "Alice", null);
        Game game = new Game();
        game.setId(2L);
        game.setNom("Halo");
        Avis avis = new Avis(1L, "Fun!", 5, user, game);

        when(avisRepository.findByGameId(2L)).thenReturn(List.of(avis));

        List<AvisDTO> result = avisService.getAvisByGame(2L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getGameId()).isEqualTo(2L);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        assertThat(result.get(0).getCommentaire()).isEqualTo("Fun!");

        verify(avisRepository).findByGameId(2L);
    }

    @Test
    void getById_shouldMapEntityToDtoCorrectly() {
        User user = new User(1L, "Alice", null);
        Game game = new Game();
        game.setId(2L);
        game.setNom("Zelda");

        Avis avis = new Avis(10L, "Très bon jeu", 5, user, game);

        when(avisRepository.findById(10L)).thenReturn(Optional.of(avis));

        AvisDTO result = avisService.getById(10L);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getCommentaire()).isEqualTo("Très bon jeu");
        assertThat(result.getNote()).isEqualTo(5);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getUserNom()).isEqualTo("Alice");
        assertThat(result.getGameId()).isEqualTo(2L);
        assertThat(result.getGameNom()).isEqualTo("Zelda");

        verify(avisRepository).findById(10L);
    }




}
