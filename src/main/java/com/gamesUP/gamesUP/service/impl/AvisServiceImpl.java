package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.AvisDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Avis;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.User;
import com.gamesUP.gamesUP.repository.AvisRepository;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.UserRepository;
import com.gamesUP.gamesUP.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AvisServiceImpl implements AvisService {

    private final AvisRepository avisRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Override
    public List<AvisDTO> getAll() {
        return avisRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AvisDTO getById(Long id) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Avis not found"));
        return toDto(avis);
    }

    @Override
    public Long create(AvisDTO dto) {
        Game game = gameRepository.findById(dto.getGameId())
                .orElseThrow(() -> new EntityDontExistException("Game not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityDontExistException("User not found"));

        Avis avis = new Avis();
        avis.setCommentaire(dto.getCommentaire());
        avis.setNote(dto.getNote());
        avis.setGame(game);
        avis.setUser(user);

        return avisRepository.save(avis).getId();
    }

    @Override
    public AvisDTO update(Long id, AvisDTO dto) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Avis not found"));

        avis.setCommentaire(dto.getCommentaire());
        avis.setNote(dto.getNote());

        return toDto(avisRepository.save(avis));
    }

    @Override
    public AvisDTO patch(Long id, AvisDTO dto) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Avis not found"));

        if (dto.getCommentaire() != null) {
            avis.setCommentaire(dto.getCommentaire());
        }

        if (dto.getNote() != null) {
            if (dto.getNote() < 1 || dto.getNote() > 5) {
                throw new IllegalArgumentException("Note must be between 1 and 5");
            }
            avis.setNote(dto.getNote());
        }

        Avis updated = avisRepository.save(avis);
        return toDto(updated);
    }


    @Override
    public void delete(Long id) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("Avis not found"));
        avisRepository.delete(avis);
    }

    @Override
    public List<AvisDTO> getAvisByUser(Long userId) {
        return avisRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvisDTO> getAvisByGame(Long gameId) {
        return avisRepository.findByGameId(gameId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AvisDTO toDto(Avis avis) {
        return new AvisDTO(
                avis.getId(),
                avis.getCommentaire(),
                avis.getNote(),
                avis.getUser().getId(),
                avis.getUser().getNom(),
                avis.getGame().getId(),
                avis.getGame().getNom()
        );
    }


}
