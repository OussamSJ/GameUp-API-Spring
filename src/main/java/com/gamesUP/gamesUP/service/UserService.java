package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    Long create(User user);
    User update(Long id, User user);
    void delete(Long id);
}
