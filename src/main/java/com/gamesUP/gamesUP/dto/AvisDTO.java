package com.gamesUP.gamesUP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvisDTO {
    private Long id;
    private String commentaire;
    private Integer note;
    private Long userId;
    private String userNom;
    private Long gameId;
    private String gameNom;

}
