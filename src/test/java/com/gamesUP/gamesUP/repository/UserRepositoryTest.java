package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save and retrieve user by ID")
    void shouldSaveAndFindUser() {
        User user = new User(1L,"Alice", "username","password","role");

        user = userRepository.save(user);

        Optional<User> found = userRepository.findById(user.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("Alice");
    }

    @Test
    @DisplayName("Should delete user")
    void shouldDeleteUser() {
        User user = new User(1L,"Alice", "username","password","role");

        user = userRepository.save(user);

        Long id = user.getId();
        userRepository.deleteById(id);

        assertThat(userRepository.findById(id)).isEmpty();
    }
}
