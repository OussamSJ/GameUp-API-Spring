package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> findAll();

    Publisher findById(Long id);

    Publisher create(Publisher publisher);

    Publisher update(Long id, Publisher publisher);

    void delete(Long id);
}
