package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserId(Long userId);
    List<Wishlist> findByGameId(Long gameId);
    Optional<Wishlist> findByUserIdAndGameId(Long userId, Long gameId);

    void deleteByGame(Game game);
}
