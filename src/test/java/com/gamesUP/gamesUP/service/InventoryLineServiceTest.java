package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.InventoryLineDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.Inventory;
import com.gamesUP.gamesUP.model.InventoryLine;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.InventoryLineRepository;
import com.gamesUP.gamesUP.repository.InventoryRepository;
import com.gamesUP.gamesUP.service.impl.InventoryLineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryLineServiceTest {

    @Mock private InventoryLineRepository lineRepo;
    @Mock private InventoryRepository inventoryRepo;
    @Mock private GameRepository gameRepo;
    @InjectMocks private InventoryLineServiceImpl service;

    @BeforeEach void setup() {MockitoAnnotations.openMocks(this);}

    @Test
    void findAll_shouldReturnList() {
        Inventory inv = new Inventory(1L, "Main Stock", null);
        Game game = new Game(1L, "Zelda", null, "Adventure", null, null, 1);
        InventoryLine line = new InventoryLine(1L, inv, game, 10);

        when(lineRepo.findAll()).thenReturn(List.of(line));

        List<InventoryLineDTO> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGameName()).isEqualTo("Zelda");
        assertThat(result.get(0).getInventoryName()).isEqualTo("Main Stock");

        verify(lineRepo).findAll();
    }

    @Test
    void findById_shouldReturnDto() {
        Inventory inv = new Inventory(1L, "Main Stock", null);
        Game game = new Game(1L, "Mario", null, "Platform", null, null, 2);
        InventoryLine line = new InventoryLine(1L, inv, game, 5);

        when(lineRepo.findById(1L)).thenReturn(Optional.of(line));

        InventoryLineDTO dto = service.findById(1L);
        assertThat(dto.getInventoryName()).isEqualTo("Main Stock");
        assertThat(dto.getQuantity()).isEqualTo(5);

        verify(lineRepo).findById(1L);
    }

    @Test
    void findById_shouldThrowIfNotFound() {
        when(lineRepo.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(100L))
                .isInstanceOf(EntityDontExistException.class)
                .hasMessageContaining("InventoryLine not found");
    }

    @Test
    void create_shouldReturnId() {
        Inventory inv = new Inventory(1L, "Depot", null);
        Game game = new Game(2L, "Tetris", null, "Puzzle", null, null, 1);
        InventoryLine line = new InventoryLine(null, inv, game, 3);
        InventoryLine saved = new InventoryLine(100L, inv, game, 3);

        InventoryLineDTO dto = new InventoryLineDTO(null, 1L, null, 2L, null, 3);

        when(inventoryRepo.findById(1L)).thenReturn(Optional.of(inv));
        when(gameRepo.findById(2L)).thenReturn(Optional.of(game));
        when(lineRepo.save(any())).thenReturn(saved);

        Long id = service.create(dto);
        assertThat(id).isEqualTo(100L);

        verify(lineRepo).save(any(InventoryLine.class));
    }

    @Test
    void update_shouldReturnUpdatedDTO() {
        Inventory inventory = new Inventory(1L, "Main", null);
        Game game = new Game();
        game.setId(2L);
        game.setNom("game");

        InventoryLine existing = new InventoryLine(3L, inventory, game, 5);

        when(lineRepo.findById(3L)).thenReturn(Optional.of(existing));
        when(inventoryRepo.findById(1L)).thenReturn(Optional.of(inventory));
        when(gameRepo.findById(2L)).thenReturn(Optional.of(game));
        when(lineRepo.save(any())).thenReturn(existing);

        InventoryLineDTO dto = new InventoryLineDTO(3L, 1L, "Main", 2L, "Mario", 10);

        InventoryLineDTO result = service.update(3L, dto);

        assertThat(result.getQuantity()).isEqualTo(10);
        assertThat(result.getGameName()).isEqualTo("game");
        verify(lineRepo).findById(3L);
        verify(lineRepo).save(existing);

    }
    @Test
    void patch_shouldUpdateOnlyProvidedFields() {
        Inventory inventory = new Inventory(1L, "Main", null);
        Game game = new Game();
        game.setId(2L);
        game.setNom("Mario");
        InventoryLine line = new InventoryLine(3L, inventory, game, 5);

        when(lineRepo.findById(3L)).thenReturn(Optional.of(line));
        when(lineRepo.save(any())).thenReturn(line);

        InventoryLineDTO partial = new InventoryLineDTO(null, null, null, null, null, 20);
        InventoryLineDTO result = service.patch(3L, partial);

        assertThat(result.getQuantity()).isEqualTo(20);

        verify(lineRepo).findById(3L);
        verify(lineRepo).save(line);
    }



    @Test
    void delete_shouldCallRepository() {
        Inventory inv = new Inventory(1L, "Stock", null);
        Game game = new Game(1L, "Game", null, "Genre", null, null, 1);
        InventoryLine line = new InventoryLine(1L, inv, game, 1);

        when(lineRepo.findById(1L)).thenReturn(Optional.of(line));
        doNothing().when(lineRepo).delete(line);

        service.delete(1L);
        verify(lineRepo).findById(1L);
        verify(lineRepo).delete(line);
    }

    @Test
    void toDto_shouldReturnCorrectDTO() throws Exception {
        Inventory inventory = new Inventory(1L, "Main Inventory", null);
        Game game = new Game();
        game.setId(1L);
        game.setNom("Game");
        InventoryLine line = new InventoryLine(3L, inventory, game, 10);

        when(lineRepo.findById(1L)).thenReturn(Optional.of(line));
        InventoryLineDTO dto = service.findById(1L);

        assertThat(dto.getId()).isEqualTo(3L);
        assertThat(dto.getInventoryName()).isEqualTo("Main Inventory");
        assertThat(dto.getGameName()).isEqualTo("Game");
        assertThat(dto.getQuantity()).isEqualTo(10);
    }

}
