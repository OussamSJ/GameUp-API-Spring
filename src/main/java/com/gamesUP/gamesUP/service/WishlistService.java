package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.WishlistGameDTO;

import java.util.List;

public interface WishlistService {
    List<WishlistGameDTO> getWishlist(Long userId);
    void addToWishlist(Long userId, Long gameId);
    void removeFromWishlist(Long userId, Long gameId);
}
