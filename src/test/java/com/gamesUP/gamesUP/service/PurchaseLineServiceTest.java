package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.Purchase;
import com.gamesUP.gamesUP.model.PurchaseLine;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.PurchaseLineRepository;
import com.gamesUP.gamesUP.repository.PurchaseRepository;
import com.gamesUP.gamesUP.service.impl.PurchaseLineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PurchaseLineServiceTest {

    @Mock private PurchaseLineRepository lineRepo;
    @Mock private GameRepository gameRepo;
    @Mock private PurchaseRepository purchaseRepo;

    @InjectMocks private PurchaseLineServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllLines() {
        Purchase purchase = new Purchase();
        Game game = new Game();
        game.setId(1L);
        game.setNom("GTA");

        List<PurchaseLine> lines = List.of(new PurchaseLine(1L, game, purchase, 2, 49.99));
        when(lineRepo.findAll()).thenReturn(lines);

        var result = service.findAll();
        assertEquals(1, result.size());

        verify(lineRepo).findAll();
    }
    @Test
    void shouldReturnLineById() {
        Game game = new Game(1L, "Zelda", null, null, null, null, 1);
        Purchase purchase = new Purchase(1L, null, null, new Date(), null);
        PurchaseLine line = new PurchaseLine(1L, game, purchase, 3, 59.99);

        when(lineRepo.findById(1L)).thenReturn(Optional.of(line));

        PurchaseLineDTO dto = service.findById(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Zelda", dto.getGameName());
        assertEquals(3, dto.getQuantite());
        assertEquals(59.99, dto.getPrix());

        verify(lineRepo).findById(1L);
    }


    @Test
    void shouldThrowIfLineNotFound() {
        when(lineRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityDontExistException.class, () -> service.findById(1L));

        verify(lineRepo).findById(1L);
    }

    @Test
    void shouldCreateLine() {
        Game game = new Game(1L, "Game", null, null, null, null, 1);
        Purchase purchase = new Purchase(2L, null, null, new Date(), null);
        PurchaseLineDTO dto = new PurchaseLineDTO(null, 2L, 1L, "Game", 3, 20.0);

        when(purchaseRepo.findById(2L)).thenReturn(Optional.of(purchase));
        when(gameRepo.findById(1L)).thenReturn(Optional.of(game));
        when(lineRepo.save(any())).thenReturn(new PurchaseLine(1L, game, purchase, 3, 20.0));

        Long id = service.create(dto);
        assertEquals(1L, id);

        verify(purchaseRepo).findById(2L);
    }

    @Test
    void shouldUpdateLine() {
        Game game = new Game(1L, "Game", null, null, null, null, 1);
        Purchase purchase = new Purchase(1L, null, null, new Date(), null);
        PurchaseLine line = new PurchaseLine(1L, game, purchase, 2, 10.0);

        PurchaseLineDTO dto = new PurchaseLineDTO(1L, 1L, 1L, "Game", 5, 25.0);

        when(lineRepo.findById(1L)).thenReturn(Optional.of(line));
        when(gameRepo.findById(1L)).thenReturn(Optional.of(game));
        when(lineRepo.save(any())).thenReturn(line);

        var result = service.update(1L, dto);
        assertEquals(5, result.getQuantite());
        assertEquals(25.0, result.getPrix());

        verify(lineRepo).findById(1L);
        verify(gameRepo).findById(1L);

    }

    @Test
    void shouldPatchLine() {
        Game game = new Game(1L, "G1", null, null, null, null, 1);
        Purchase purchase = new Purchase(1L, null, null, new Date(), null);
        PurchaseLine line = new PurchaseLine(1L, game, purchase, 2, 10.0);

        when(lineRepo.findById(1L)).thenReturn(Optional.of(line));
        when(gameRepo.findById(1L)).thenReturn(Optional.of(game));
        when(lineRepo.save(any())).thenReturn(line);

        var patched = service.patch(1L, new PurchaseLineDTO(null, null, 1L, null, 3, 19.0));
        assertEquals(3, patched.getQuantite());

        verify(lineRepo).findById(1L);
        verify(gameRepo).findById(1L);

    }

    @Test
    void shouldDeleteLine() {
        Game game = new Game(1L, "Game", null, null, null, null, 1);
        Purchase purchase = new Purchase();
        PurchaseLine line = new PurchaseLine(1L, game, purchase, 2, 49.99);

        when(lineRepo.findById(1L)).thenReturn(Optional.of(line));
        service.delete(1L);
        verify(lineRepo).delete(line);
    }

    @Test
    void shouldMapEntityToDTO() {
        Game game = new Game(2L, "Minecraft", null, null, null, null, 1);
        Purchase purchase = new Purchase(3L, null, null, new Date(), null);
        PurchaseLine line = new PurchaseLine(10L, game, purchase, 7, 9.99);

        PurchaseLineDTO dto = service.mapToDTO(line);

        assertEquals(10L, dto.getId());
        assertEquals(2L, dto.getGameId());
        assertEquals("Minecraft", dto.getGameName());
        assertEquals(3L, dto.getPurchaseId());
        assertEquals(7, dto.getQuantite());
        assertEquals(9.99, dto.getPrix());
    }

}
