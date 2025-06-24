package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.WishlistGameDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.*;
import com.gamesUP.gamesUP.repository.*;
import com.gamesUP.gamesUP.service.impl.WishlistServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;

    private User user;
    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "John", null,null,null);
        game = new Game(1L, "Zelda", new Author(1L, "Author",null), "Adventure", new Category(1L, "RPG",null),new Publisher(1L, "Publisher", null), 1);
    }

    @Test
    void shouldReturnWishlist() {
        Wishlist wishlist = new Wishlist(1L, user, game);

        when(wishlistRepository.findByUserId(1L)).thenReturn(List.of(wishlist));

        List<WishlistGameDTO> result = wishlistService.getWishlist(1L);

        assertEquals(1, result.size());
        assertEquals("Zelda", result.get(0).getNom());
        assertEquals("Author", result.get(0).getAuthorName());
        assertEquals("Publisher", result.get(0).getPublisherName());
        assertEquals("RPG", result.get(0).getType());
    }

    @Test
    void shouldAddToWishlist() {
        when(wishlistRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        assertDoesNotThrow(() -> wishlistService.addToWishlist(1L, 1L));

        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    void shouldNotAddDuplicateWishlist() {
        Wishlist wishlist = new Wishlist(1L, user, game);
        when(wishlistRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.of(wishlist));

        wishlistService.addToWishlist(1L, 1L);

        verify(wishlistRepository, never()).save(any());
    }
    @Test
    void shouldThrowWhenRemovingNonExistentWishlist() {
        when(wishlistRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(EntityDontExistException.class, () -> wishlistService.removeFromWishlist(1L, 1L));
    }
    @Test
    void shouldThrowWhenUserNotFoundOnAdd() {
        when(wishlistRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityDontExistException.class, () -> wishlistService.addToWishlist(1L, 1L));
    }

    @Test
    void shouldThrowWhenGameNotFoundOnAdd() {
        when(wishlistRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityDontExistException.class, () -> wishlistService.addToWishlist(1L, 1L));
    }



    @Test
    void shouldRemoveFromWishlist() {
        Wishlist wishlist = new Wishlist(1L, user, game);

        when(wishlistRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.of(wishlist));

        wishlistService.removeFromWishlist(1L, 1L);

        verify(wishlistRepository).delete(wishlist);
    }


}
