package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WishlistTest {

    @Test
    void shouldCreateWishlistWithFields() {
        User user = new User();
        user.setId(1L);
        user.setNom("Alice");

        Game game = new Game();
        game.setId(2L);
        game.setNom("Chess");

        Wishlist wishlist = new Wishlist(10L, user, game);

        assertThat(wishlist.getId()).isEqualTo(10L);
        assertThat(wishlist.getUser()).isEqualTo(user);
        assertThat(wishlist.getGame()).isEqualTo(game);
    }

    @Test
    void shouldSetAndGetFields() {
        Wishlist wishlist = new Wishlist();

        User user = new User();
        user.setId(5L);
        user.setNom("Bob");

        Game game = new Game();
        game.setId(6L);
        game.setNom("Monopoly");

        wishlist.setId(3L);
        wishlist.setUser(user);
        wishlist.setGame(game);

        assertThat(wishlist.getId()).isEqualTo(3L);
        assertThat(wishlist.getUser()).isEqualTo(user);
        assertThat(wishlist.getGame()).isEqualTo(game);
    }
}
