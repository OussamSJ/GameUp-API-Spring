package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.AvisDTO;

import java.util.List;

public interface AvisService {


    List<AvisDTO> getAll();

    AvisDTO getById(Long id);

    Long create(AvisDTO dto);

    AvisDTO update(Long id, AvisDTO dto);
    AvisDTO patch(Long id, AvisDTO dto);


    void delete(Long id);
    List<AvisDTO> getAvisByUser(Long userId);
    List<AvisDTO> getAvisByGame(Long gameId);

}
