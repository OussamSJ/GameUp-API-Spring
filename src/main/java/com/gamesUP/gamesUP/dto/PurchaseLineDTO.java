package com.gamesUP.gamesUP.dto;

import com.gamesUP.gamesUP.model.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PurchaseLineDTO {

    private Long id;
    private Long PurchaseId;
    private Long userId;
    private String userName;
    private Long gameId;
    private String gameName;
    private int quantite;
    private double prix;
    private Date date;
}
