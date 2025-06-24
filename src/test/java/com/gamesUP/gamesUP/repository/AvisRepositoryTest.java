package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AvisRepositoryTest {

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should save and retrieve an Avis")
    void shouldSaveAndRetrieveAvis() {

        User user = new User(1L,"Alice", "username","password","role");

        user = userRepository.save(user);

        Game game = buildAndSaveValidGame("Zelda");

        Avis avis = new Avis();
        avis.setCommentaire("Très bon jeu !");
        avis.setNote(5);
        avis.setUser(user);
        avis.setGame(game);

        avis = avisRepository.save(avis);

        // Act
        Avis found = avisRepository.findById(avis.getId()).orElse(null);

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(avis.getId());
        assertThat(found.getCommentaire()).isEqualTo("Très bon jeu !");
        assertThat(found.getNote()).isEqualTo(5);
        assertThat(found.getUser().getNom()).isEqualTo("Alice");
        assertThat(found.getGame().getNom()).isEqualTo("Zelda");
    }

    @Test
    @DisplayName("Should find Avis by User ID")
    void shouldFindAvisByUserId() {
        User user = new User(1L,"Bob","username","password","role");
        user = userRepository.save(user);

        Game game = buildAndSaveValidGame("Mario");

        Avis avis = new Avis(null, "Sympa", 4, user, game);
        avisRepository.save(avis);

        List<Avis> results = avisRepository.findByUserId(user.getId());

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getUser().getNom()).isEqualTo("Bob");
    }

    @Test
    @DisplayName("Should find Avis by Game ID")
    void shouldFindAvisByGameId() {
        User user = new User(1L,"Charlie", "username","password","role");

        user = userRepository.save(user);

        Game game = buildAndSaveValidGame("Sonic");

        Avis avis = new Avis(null, "Rapide et fun", 5, user, game);
        avisRepository.save(avis);

        List<Avis> results = avisRepository.findByGameId(game.getId());

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCommentaire()).isEqualTo("Rapide et fun");
    }

    @Test
    @DisplayName("Should delete an Avis")
    void shouldDeleteAvis() {
        User user = new User(1L,"David", "username","password","role");

        user = userRepository.save(user);

        Game game = buildAndSaveValidGame("Pac-Man");

        Avis avis = new Avis(null, "Classique", 3, user, game);
        avis = avisRepository.save(avis);

        Long id = avis.getId();

        // Delete
        avisRepository.deleteById(id);

        assertThat(avisRepository.findById(id)).isEmpty();
    }

    // Helper method
    private Game buildAndSaveValidGame(String nom) {
        Author author = new Author();
        author.setName("Author " + nom);
        author = authorRepository.save(author);

        Category category = new Category();
        category.setType("Category " + nom);
        category = categoryRepository.save(category);

        Game game = new Game();
        game.setNom(nom);
        game.setGenre("Genre " + nom);
        game.setAuthor(author);
        game.setCategory(category);
        return gameRepository.save(game);
    }
}
