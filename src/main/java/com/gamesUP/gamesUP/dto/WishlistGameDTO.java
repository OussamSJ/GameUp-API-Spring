package com.gamesUP.gamesUP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistGameDTO {
    private Long userId;
    private Long gameId;
    private String nom;
    private String genre;
    private String authorName;
    private String publisherName;
    private String type;
    private int numEdition;
    // Optionnel : date d'ajout si tu la stockes dans Wishlist (voir note ci-dessous)
    // private LocalDateTime dateAdded;
}
