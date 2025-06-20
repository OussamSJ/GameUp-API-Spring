package com.gamesUP.gamesUP.dto;

import com.gamesUP.gamesUP.model.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Date date;
    private PurchaseStatus status;
    private List<PurchaseLineDTO> lines;
}
