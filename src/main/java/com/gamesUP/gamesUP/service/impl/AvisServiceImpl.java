package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Avis;
import com.gamesUP.gamesUP.model.Game;
import com.gamesUP.gamesUP.model.User;
import com.gamesUP.gamesUP.repository.AvisRepository;
import com.gamesUP.gamesUP.repository.GameRepository;
import com.gamesUP.gamesUP.repository.UserRepository;
import com.gamesUP.gamesUP.service.AvisService;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AvisServiceImpl implements AvisService {

    private final AvisRepository avisRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Override
    public List<Avis> findAll() {
        return avisRepository.findAll();
    }

    @Override
    public Avis findById(Long id) {
        return avisRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Avis not found"));
    }

    @Override
    public Avis create(Avis avis) {
        //Vérifier que le jeu existe déjà
        Game game = gameRepository.findById(avis.getGame().getId())
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
        //Vérifier que l'utilisateur existe déjà
        User user = userRepository.findById(avis.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return avisRepository.save(avis);
    }

    @Override
    public Avis update(Long id, Avis avis) {
        if (!avisRepository.existsById(id)) {
            throw new EntityNotFoundException("Avis not found");
        }
        avis.setId(id);
        return avisRepository.save(avis);
    }

    @Override
    public void patch(Long id, Avis partialAvis) {
        Avis existing = avisRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException());

        if (partialAvis.getCommentaire() != null) {
            existing.setCommentaire(partialAvis.getCommentaire());
        }

        if (partialAvis.getNote() != 0) { // Attention : 0 peut être une valeur valide
            existing.setNote(partialAvis.getNote());
        }

        if (partialAvis.getUser() != null) {
            existing.setUser(partialAvis.getUser());
        }

        if (partialAvis.getGame() != null) {
            existing.setGame(partialAvis.getGame());
        }

        avisRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Avis existing = avisRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException());
        avisRepository.deleteById(id);
    }

    @Override
    public List<Avis> getAvisByGameId(Long gameId) {
        //Vérifier que le jeux existe déjà
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
        return avisRepository.findByGameId(gameId);
    }

    @Override
    public List<Avis> getAvisByUserId(Long userId) {
        //Vérifier que l'utilisateur existe déjà
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return avisRepository.findByUserId(userId);
    }
}
