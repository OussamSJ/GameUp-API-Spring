package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.PurchaseLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseLineRepository extends JpaRepository<PurchaseLine,Long> {
    Optional<PurchaseLine> findById(Long id);

}
