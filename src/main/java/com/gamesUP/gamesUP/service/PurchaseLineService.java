package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import java.util.List;

public interface PurchaseLineService {
    List<PurchaseLineDTO> findAll();
    PurchaseLineDTO findById(Long id);
    Long create(PurchaseLineDTO dto);
    PurchaseLineDTO update(Long id, PurchaseLineDTO dto);
    PurchaseLineDTO patch(Long id, PurchaseLineDTO dto);
    void delete(Long id);
}

