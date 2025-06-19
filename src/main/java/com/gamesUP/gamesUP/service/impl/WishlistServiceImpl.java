package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.WishlistGameDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.User;
import com.gamesUP.gamesUP.model.Wishlist;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.UserRepository;
import com.gamesUP.gamesUP.repository.WishlistRepository;
import com.gamesUP.gamesUP.service.WishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final WishlistRepository wishlistRepository;


    @Override
    public List<WishlistGameDTO> getWishlist(Long userId) {
        List<Wishlist> items = wishlistRepository.findByUserId(userId);

        return items.stream()
                .map(item -> {
                    Game game = item.getGame();
                    return new WishlistGameDTO(
                            userId,
                            game.getId(),
                            game.getNom(),
                            game.getGenre(),
                            game.getAuthor() != null ? game.getAuthor().getName() : null,
                            game.getPublisher() != null ? game.getPublisher().getName() : null,
                            game.getCategory() != null ? game.getCategory().getType() : null,
                            game.getNumEdition()
                            // dateAdded à ajouter si tu modifies l'entité Wishlist pour l'inclure
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addToWishlist(Long userId, Long gameId) {
        if (wishlistRepository.findByUserIdAndGameId(userId, gameId).isPresent()) {
            return; // déjà présent
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDontExistException("User not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityDontExistException("Game not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setGame(game);

        wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void removeFromWishlist(Long userId, Long gameId) {
        Wishlist wishlist = wishlistRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new EntityDontExistException("Wishlist entry not found"));

        wishlistRepository.delete(wishlist);
    }
}
