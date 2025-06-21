package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PurchaseLineRepositoryTest {

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    @DisplayName("Save and retrieve a PurchaseLine")
    void testSaveAndRetrieve() {
        Game game = new Game();
        game.setNom("Test Game");
        game.setGenre("Action");
        Author author = authorRepository.save(new Author(null, "Author Test", null));
        Category category = categoryRepository.save(new Category(null, "Category Test",null));
        Publisher publisher = publisherRepository.save(new Publisher(null, "Publisher Test",null));
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game = gameRepository.save(game);

        User user = new User();
        user.setNom("Client1");
        user = userRepository.save(user);

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setDate(new Date());
        purchase.setStatus(PurchaseStatus.ARCHIVED);
        purchase = purchaseRepository.save(purchase);

        PurchaseLine line = new PurchaseLine(null, game, purchase, 4, 20.0);
        PurchaseLine savedLine = purchaseLineRepository.save(line);

        assertThat(savedLine.getId()).isNotNull();
        assertThat(savedLine.getGame().getNom()).isEqualTo("Test Game");
        assertThat(savedLine.getPurchase().getUser().getNom()).isEqualTo("Client1");
        assertThat(savedLine.getQuantite()).isEqualTo(4);
    }


}
