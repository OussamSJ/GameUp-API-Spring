package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    Optional<Publisher> findByName(String name);
}
