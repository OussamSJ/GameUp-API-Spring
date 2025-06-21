package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InventoryLineRepositoryTest {

    @Autowired
    private InventoryLineRepository inventoryLineRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Save and retrieve an InventoryLine entity")
    void testSaveAndFind() {
        Inventory inventory = new Inventory();
        inventory.setName("Inventory for Test");
        inventory = inventoryRepository.save(inventory);

        // Création entités obligatoires pour Game
        Author author = new Author();
        author.setName("Test Author");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setType("Test Category");
        category = categoryRepository.save(category);

        Game game = new Game();
        game.setNom("Game for Test");
        game.setGenre("GenreTest");
        game.setAuthor(author);
        game.setCategory(category);


        game = gameRepository.save(game);

        InventoryLine line = new InventoryLine();
        line.setInventory(inventory);
        line.setGame(game);
        line.setQuantity(15);

        InventoryLine saved = inventoryLineRepository.save(line);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getQuantity()).isEqualTo(15);
        assertThat(saved.getInventory().getId()).isEqualTo(inventory.getId());
        assertThat(saved.getGame().getId()).isEqualTo(game.getId());

        InventoryLine found = inventoryLineRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getQuantity()).isEqualTo(15);
    }

    @Test
    @DisplayName("Test findByInventoryId returns all lines for the inventory")
    void testFindByInventoryId() {
        Inventory inventory1 = new Inventory();
        inventory1.setName("Inventory 1");
        inventory1 = inventoryRepository.save(inventory1);

        Inventory inventory2 = new Inventory();
        inventory2.setName("Inventory 2");
        inventory2 = inventoryRepository.save(inventory2);

        // Création entités obligatoires pour Game
        Author author = new Author();
        author.setName("Author 1");
        author = authorRepository.save(author);

        Category category = new Category();
        category.setType("Category 1");
        category = categoryRepository.save(category);

        Game game = new Game();
        game.setNom("Game 1");
        game.setGenre("Genre");
        game.setAuthor(author);
        game.setCategory(category);
        game = gameRepository.save(game);

        InventoryLine line1 = new InventoryLine(null, inventory1, game, 10);
        InventoryLine line2 = new InventoryLine(null, inventory1, game, 5);
        InventoryLine line3 = new InventoryLine(null, inventory2, game, 7);

        inventoryLineRepository.save(line1);
        inventoryLineRepository.save(line2);
        inventoryLineRepository.save(line3);

        List<InventoryLine> result = inventoryLineRepository.findByInventoryId(inventory1.getId());
        assertThat(result).hasSize(2);
        Inventory finalInventory = inventory1;
        assertThat(result).allMatch(line -> line.getInventory().getId().equals(finalInventory.getId()));

        List<InventoryLine> result2 = inventoryLineRepository.findByInventoryId(inventory2.getId());
        assertThat(result2).hasSize(1);
        assertThat(result2.get(0).getInventory().getId()).isEqualTo(inventory2.getId());
    }

    @Test
    @DisplayName("Test findByGameId returns all lines for the game")
    void testFindByGameId() {
        Inventory inventory = new Inventory();
        inventory.setName("Inventory A");
        inventory = inventoryRepository.save(inventory);

        // Création entités obligatoires pour game1
        Author author1 = new Author();
        author1.setName("Author A");
        author1 = authorRepository.save(author1);

        Category category1 = new Category();
        category1.setType("Category A");
        category1 = categoryRepository.save(category1);

        Game game1 = new Game();
        game1.setNom("Game 1");
        game1.setGenre("Genre");
        game1.setAuthor(author1);
        game1.setCategory(category1);
        game1 = gameRepository.save(game1);

        // Création entités obligatoires pour game2
        Author author2 = new Author();
        author2.setName("Author B");
        author2 = authorRepository.save(author2);

        Category category2 = new Category();
        category2.setType("Category B");
        category2 = categoryRepository.save(category2);

        Game game2 = new Game();
        game2.setNom("Game 2");
        game2.setGenre("Genre");
        game2.setAuthor(author2);
        game2.setCategory(category2);
        game2 = gameRepository.save(game2);

        InventoryLine line1 = new InventoryLine(null, inventory, game1, 10);
        InventoryLine line2 = new InventoryLine(null, inventory, game2, 5);
        InventoryLine line3 = new InventoryLine(null, inventory, game1, 7);

        inventoryLineRepository.save(line1);
        inventoryLineRepository.save(line2);
        inventoryLineRepository.save(line3);

        List<InventoryLine> result = inventoryLineRepository.findByGameId(game1.getId());
        assertThat(result).hasSize(2);
        Game finalGame = game1;
        assertThat(result).allMatch(line -> line.getGame().getId().equals(finalGame.getId()));

        List<InventoryLine> result2 = inventoryLineRepository.findByGameId(game2.getId());
        assertThat(result2).hasSize(1);
        assertThat(result2.get(0).getGame().getId()).isEqualTo(game2.getId());
    }
}
