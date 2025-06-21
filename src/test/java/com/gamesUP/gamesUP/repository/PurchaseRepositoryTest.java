package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Test
    @DisplayName("Should save and retrieve a Purchase")
    void shouldSaveAndRetrievePurchase() {
        User user = new User();
        user.setNom("Alice");
        user = userRepository.save(user);

        Game game = new Game();
        game.setNom("Chess");
        game.setGenre("Strategy");
        Author author = authorRepository.save(new Author(null, "Author Test", null));
        Category category = categoryRepository.save(new Category(null, "Category Test",null));
        Publisher publisher = publisherRepository.save(new Publisher(null, "Publisher Test",null));
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game = gameRepository.save(game);

        Purchase purchase = new Purchase();
        purchase.setUser(user);

        purchase.setDate(new Date());
        purchase.setStatus(PurchaseStatus.PAID);

        PurchaseLine line = new PurchaseLine();
        line.setGame(game);
        line.setQuantite(2);
        line.setPrix(29.99);
        line.setPurchase(purchase);

        purchase.setLines(Collections.singletonList(line));

        Purchase saved = purchaseRepository.save(purchase);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUser().getId()).isEqualTo(user.getId());
        assertThat(saved.getLines()).hasSize(1);
        assertThat(saved.getStatus()).isEqualTo(PurchaseStatus.PAID);
    }

    @Test
    @DisplayName("Should delete a Purchase")
    void shouldDeletePurchase() {
        User user = new User();
        user.setNom("Bob");
        user = userRepository.save(user);

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setDate(new Date());
        purchase.setStatus(PurchaseStatus.PAID);
        purchase = purchaseRepository.save(purchase);

        Long purchaseId = purchase.getId();
        purchaseRepository.deleteById(purchaseId);

        assertThat(purchaseRepository.findById(purchaseId)).isEmpty();
    }

    @Test
    @DisplayName("Should delete a PurchaseLine")
    void shouldDeletePurchaseLine() {
        // Créer les entités nécessaires : Game, User, Purchase
        Game game = new Game();
        game.setNom("DeleteTestGame");
        game.setGenre("TestGenre");
        Author author = authorRepository.save(new Author(null, "Author Test", null));
        Category category = categoryRepository.save(new Category(null, "Category Test",null));
        Publisher publisher = publisherRepository.save(new Publisher(null, "Publisher Test",null));
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game = gameRepository.save(game);

        User user = new User();
        user.setNom("DeleteUser");
        user = userRepository.save(user);

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setDate(new Date());
        purchase.setStatus(PurchaseStatus.ARCHIVED);
        purchase = purchaseRepository.save(purchase);

        // Créer une ligne de commande (PurchaseLine)
        PurchaseLine line = new PurchaseLine();
        line.setGame(game);
        line.setPurchase(purchase);
        line.setQuantite(2);
        line.setPrix(19.99);
        line = purchaseLineRepository.save(line);

        Long lineId = line.getId();

        // Supprimer
        purchaseLineRepository.deleteById(lineId);

        // Vérifier que la ligne a été supprimée
        boolean exists = purchaseLineRepository.findById(lineId).isPresent();
        assertThat(exists).isFalse();
    }


}
