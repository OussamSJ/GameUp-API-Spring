package com.gamesUP.gamesUP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryLineDTO {

    private Long id;
    private Long inventoryId;
    private String inventoryName;
    private Long gameId;
    private String gameName;
    private int quantity;
}
