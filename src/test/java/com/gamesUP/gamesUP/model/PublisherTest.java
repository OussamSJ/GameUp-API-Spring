package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PublisherTest {

    @Test
    @DisplayName("Should create Publisher using constructor and getters")
    void testConstructorAndGetters() {
        Set<Game> games = new HashSet<>();

        Publisher publisher = new Publisher(1L, "Nintendo", games);

        assertThat(publisher.getId()).isEqualTo(1L);
        assertThat(publisher.getName()).isEqualTo("Nintendo");
        assertThat(publisher.getGames()).isEqualTo(games);
    }

    @Test
    @DisplayName("Should set and get fields")
    void testSettersAndGetters() {
        Publisher publisher = new Publisher();

        publisher.setId(2L);
        publisher.setName("Sony");

        Game game = new Game();
        game.setNom("Uncharted");

        Set<Game> games = new HashSet<>();
        games.add(game);

        publisher.setGames(games);

        assertThat(publisher.getId()).isEqualTo(2L);
        assertThat(publisher.getName()).isEqualTo("Sony");
        assertThat(publisher.getGames()).hasSize(1);
        assertThat(publisher.getGames().iterator().next().getNom()).isEqualTo("Uncharted");
    }

    @Test
    @DisplayName("Should add and remove games from publisher")
    void testGamesManagement() {
        Publisher publisher = new Publisher();
        Game game1 = new Game();
        game1.setNom("Game 1");

        Game game2 = new Game();
        game2.setNom("Game 2");

        Set<Game> games = new HashSet<>();
        games.add(game1);
        games.add(game2);

        publisher.setGames(games);

        assertThat(publisher.getGames()).containsExactlyInAnyOrder(game1, game2);

        publisher.getGames().remove(game1);
        assertThat(publisher.getGames()).containsExactly(game2);
    }
}
