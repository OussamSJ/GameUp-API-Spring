package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.dto.WishlistGameDTO;
import com.gamesUP.gamesUP.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/{userId}")
    public List<WishlistGameDTO> getWishlist(@PathVariable Long userId) {
        return wishlistService.getWishlist(userId);
    }

    @PutMapping("/{userId}/game/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToWishlist(@PathVariable Long userId, @PathVariable Long gameId) {
        wishlistService.addToWishlist(userId, gameId);
    }

    @DeleteMapping("/{userId}/game/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromWishlist(@PathVariable Long userId, @PathVariable Long gameId) {
        wishlistService.removeFromWishlist(userId, gameId);
    }
}
