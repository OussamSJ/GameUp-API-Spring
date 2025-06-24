package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

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



    @Test
    @DisplayName("Should save and retrieve a Wishlist entry")
    void shouldSaveAndRetrieveWishlist() {
        User user = new User(1L,"Alice", "username","password","role");

        user = userRepository.save(user);

        Game game = new Game();
        game.setNom("GameTest");
        Author author = authorRepository.save(new Author(null, "Author Test", null));
        Category category = categoryRepository.save(new Category(null, "Category Test",null));
        Publisher publisher = publisherRepository.save(new Publisher(null, "Publisher Test",null));
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game = gameRepository.save(game);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setGame(game);

        wishlist = wishlistRepository.save(wishlist);

        Optional<Wishlist> found = wishlistRepository.findById(wishlist.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getUser().getId()).isEqualTo(user.getId());
        assertThat(found.get().getGame().getId()).isEqualTo(game.getId());
    }

    @Test
    @DisplayName("Should delete a Wishlist entry")
    void shouldDeleteWishlist() {
        User user = new User(1L,"Alice", "username","password","role");

        user = userRepository.save(user);

        Game game = new Game();
        game.setNom("GameDelete");
        Author author = authorRepository.save(new Author(null, "Author Test", null));
        Category category = categoryRepository.save(new Category(null, "Category Test",null));
        Publisher publisher = publisherRepository.save(new Publisher(null, "Publisher Test",null));
        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);

        game = gameRepository.save(game);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setGame(game);

        wishlist = wishlistRepository.save(wishlist);

        Long id = wishlist.getId();

        wishlistRepository.deleteById(id);

        assertThat(wishlistRepository.findById(id)).isEmpty();
    }
}
