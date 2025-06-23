package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.PurchaseDTO;
import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.*;
import com.gamesUP.gamesUP.repository.*;
import com.gamesUP.gamesUP.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PurchaseServiceTest {

    private PurchaseServiceImpl service;
    private PurchaseRepository purchaseRepo;
    private PurchaseLineRepository lineRepo;
    private GameRepository gameRepo;
    private UserRepository userRepo;

    @BeforeEach
    void setup() {
        purchaseRepo = mock(PurchaseRepository.class);
        lineRepo = mock(PurchaseLineRepository.class);
        gameRepo = mock(GameRepository.class);
        userRepo = mock(UserRepository.class);
        service = new PurchaseServiceImpl(purchaseRepo, lineRepo, gameRepo, userRepo);
    }

    @Test
    void shouldFindById() {
        User user = new User();
        user.setId(1L);
        user.setNom("admin");
        Purchase purchase = new Purchase(1L, user, new ArrayList<>(), new Date(), PurchaseStatus.PAID);

        when(purchaseRepo.findById(1L)).thenReturn(Optional.of(purchase));

        PurchaseDTO result = service.findById(1L);

        assertEquals(1L, result.getId());
        verify(purchaseRepo).findById(1L);
    }




    @Test
    void shouldThrowWhenNotFound() {
        when(purchaseRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityDontExistException.class, () -> service.findById(1L));
    }

    @Test
    void shouldCreatePurchase() {
        User user = new User();
        user.setId(1L);
        user.setNom("admin");
        Game game = new Game(1L, "Game", null, "genre", null, null, 1);
        PurchaseDTO dto = new PurchaseDTO(null, 1L, "John", new Date(), PurchaseStatus.PAID,
                List.of(new PurchaseLineDTO(null, null, 1L, "Game", 1, 10.0)));

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepo.findById(1L)).thenReturn(Optional.of(game));
        when(purchaseRepo.save(any())).thenAnswer(i -> {
            Purchase p = i.getArgument(0);
            p.setId(42L);
            return p;
        });

        Long id = service.create(dto);

        assertEquals(42L, id);
    }

    @Test
    void shouldUpdatePurchase() {

        User user = new User();
        user.setId(1L);
        user.setNom("admin");
        Purchase existing = new Purchase(1L, user, new ArrayList<>(), new Date(), PurchaseStatus.PAID);
        Game game = new Game(1L, "Game", null, "Action", null, null, 1);

        PurchaseLineDTO lineDTO = new PurchaseLineDTO(null, 1L, 1L, "Game", 2, 19.99);
        PurchaseDTO dto = new PurchaseDTO(1L, 1L, "User", new Date(), PurchaseStatus.PAID, List.of(lineDTO));

        when(purchaseRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepo.findById(1L)).thenReturn(Optional.of(existing.getUser()));
        when(gameRepo.findById(1L)).thenReturn(Optional.of(game));
        when(purchaseRepo.save(any(Purchase.class))).thenAnswer(i -> i.getArgument(0));

        PurchaseDTO result = service.update(1L, dto);

        assertEquals(dto.getStatus(), result.getStatus());
        verify(purchaseRepo).save(any(Purchase.class));
    }

    @Test
    void shouldPatchPurchase() {
        User user = new User();
        user.setId(1L);
        user.setNom("admin");
        Purchase purchase = new Purchase(1L, user, new ArrayList<>(), new Date(), PurchaseStatus.PAID);
        PurchaseDTO dto = new PurchaseDTO(1L, 1L, "User", new Date(), PurchaseStatus.PAID, new ArrayList<>());

        when(purchaseRepo.findById(1L)).thenReturn(Optional.of(purchase));
        when(userRepo.findById(1L)).thenReturn(Optional.of(purchase.getUser()));
        when(purchaseRepo.save(any(Purchase.class))).thenAnswer(i -> i.getArgument(0));

        PurchaseDTO result = service.patch(1L, dto);

        assertEquals(dto.getStatus(), result.getStatus());
    }
    @Test
    void shouldDelete() {
        Purchase purchase = new Purchase(1L, new User(), List.of(), new Date(), PurchaseStatus.PAID);
        when(purchaseRepo.findById(1L)).thenReturn(Optional.of(purchase));

        service.delete(1L);

        verify(purchaseRepo).delete(purchase);
    }

    @Test
    void shouldMapToDTO() {
        User user = new User();
        user.setId(1L);
        user.setNom("admin");

        Game game = new Game(1L, "Game", null, "Action", null, null, 1);
        Purchase purchase = new Purchase(1L, user, new ArrayList<>(), new Date(), PurchaseStatus.PAID);
        PurchaseLine line = new PurchaseLine(1L, game, purchase, 2, 15.0);
        purchase.setLines(List.of(line));

        when(purchaseRepo.findById(1L)).thenReturn(Optional.of(purchase));

        PurchaseDTO result = service.findById(1L);

        assertEquals(user.getNom(), result.getUserName());
        assertEquals(1, result.getLines().size());
        assertEquals(1, result.getLines().get(0).getGameId());
    }


    @Test
    void shouldMapFromDTO() {
        User user = new User();
        user.setId(1L);
        user.setNom("admin");

        Game game = new Game(1L, "Game", null, "Action", null, null, 1);

        PurchaseLineDTO lineDTO = new PurchaseLineDTO(null, null, 1L, "Game", 2, 20.0);
        PurchaseDTO dto = new PurchaseDTO(null, 1L, "admin", new Date(), PurchaseStatus.PAID, List.of(lineDTO));

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepo.findById(1L)).thenReturn(Optional.of(game));
        when(purchaseRepo.save(any(Purchase.class))).thenAnswer(invocation -> {
            Purchase saved = invocation.getArgument(0);
            saved.setId(100L);
            return saved;
        });

        Long id = service.create(dto);

        assertNotNull(id);
        assertEquals(100L, id);
        verify(purchaseRepo).save(any(Purchase.class));
    }


}
