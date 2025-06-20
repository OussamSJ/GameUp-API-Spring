package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.User;
import com.gamesUP.gamesUP.repository.UserRepository;
import com.gamesUP.gamesUP.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityDontExistException("User not found"));
    }

    @Override
    public Long create(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public User update(Long id, User user) {
        User existing = findById(id);
        existing.setNom(user.getNom());
        existing.setAvis(user.getAvis());
        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        User existing = findById(id);
        userRepository.delete(existing);
    }

}
