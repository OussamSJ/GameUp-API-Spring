package com.gamesUP.gamesUP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {

    private Long gameId;
    private String nom;
    private String genre;
    private String authorName;
    private String publisherName;
    private String Category;
    private int numEdition;

}
