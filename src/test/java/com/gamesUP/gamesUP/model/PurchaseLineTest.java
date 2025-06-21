package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseLineTest {

    @Test
    void testSettersAndGetters() {
        Game game = new Game();
        game.setId(1L);
        game.setNom("FIFA");

        Purchase purchase = new Purchase();
        purchase.setId(10L);

        PurchaseLine line = new PurchaseLine();
        line.setId(5L);
        line.setGame(game);
        line.setPurchase(purchase);
        line.setQuantite(3);
        line.setPrix(59.99);

        assertThat(line.getId()).isEqualTo(5L);
        assertThat(line.getGame()).isEqualTo(game);
        assertThat(line.getPurchase()).isEqualTo(purchase);
        assertThat(line.getQuantite()).isEqualTo(3);
        assertThat(line.getPrix()).isEqualTo(59.99);
    }

    @Test
    void testAllArgsConstructor() {
        Game game = new Game();
        game.setNom("Mario Kart");

        Purchase purchase = new Purchase();

        PurchaseLine line = new PurchaseLine(7L, game, purchase, 2, 29.99);

        assertThat(line.getId()).isEqualTo(7L);
        assertThat(line.getGame().getNom()).isEqualTo("Mario Kart");
        assertThat(line.getPurchase()).isEqualTo(purchase);
        assertThat(line.getQuantite()).isEqualTo(2);
        assertThat(line.getPrix()).isEqualTo(29.99);
    }
}
