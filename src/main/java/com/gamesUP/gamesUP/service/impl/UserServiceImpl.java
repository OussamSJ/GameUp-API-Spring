package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.RegisterDTO;
import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.User;
import com.gamesUP.gamesUP.repository.UserRepository;
import com.gamesUP.gamesUP.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    public Long create(RegisterDTO registerDTO) {

        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new EntityDontExistException("Username already exists");
        }

        User user = new User();
        user.setNom(registerDTO.getNom());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole("ROLE_USER");

        return userRepository.save(user).getId();
    }



    @Override
    public User update(Long id, User user) {
        User existing = findById(id);
        existing.setNom(user.getNom());
        existing.setUsername(user.getUsername());
        existing.setRole(user.getRole());
        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        User existing = findById(id);
        userRepository.delete(existing);
    }

}
