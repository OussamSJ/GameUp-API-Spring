package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Author;
import com.gamesUP.gamesUP.model.Category;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    @DisplayName("Save and retrieve a Game entity")
    void testSaveAndFind() {
        // Création des entités liées
        Author author = authorRepository.save(new Author(null, "Author Test", null));
        Category category = categoryRepository.save(new Category(null, "Category Test",null));
        Publisher publisher = publisherRepository.save(new Publisher(null, "Publisher Test",null));

        // Création du jeu
        Game game = new Game();
        game.setNom("Test Game");
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game.setGenre("Action");
        game.setNumEdition(1);

        // Sauvegarder le jeu
        Game saved = gameRepository.save(game);

        // Vérifier que le jeu est bien sauvegardé
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNom()).isEqualTo("Test Game");

        // Récupérer depuis la base
        Game found = gameRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getAuthor().getName()).isEqualTo("Author Test");
        assertThat(found.getCategory().getType()).isEqualTo("Category Test");
        assertThat(found.getPublisher().getName()).isEqualTo("Publisher Test");
    }
}
