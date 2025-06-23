package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.User;
import com.gamesUP.gamesUP.repository.UserRepository;
import com.gamesUP.gamesUP.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldFindAllUsers() {
        List<User> users = List.of(new User(1L, "John", new HashSet<>()));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getNom());
    }

    @Test
    void shouldFindUserById() {
        User user = new User(1L, "Alice", new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertEquals("Alice", result.getNom());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityDontExistException.class, () -> userService.findById(99L));
    }

    @Test
    void shouldCreateUser() {
        User user = new User(null, "New User", new HashSet<>());
        User saved = new User(1L, "New User", new HashSet<>());
        when(userRepository.save(user)).thenReturn(saved);

        Long id = userService.create(user);

        assertEquals(1L, id);
    }

    @Test
    void shouldUpdateUser() {
        User existing = new User(1L, "Old Name", new HashSet<>());
        User updated = new User(null, "Updated Name", new HashSet<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.update(1L, updated);

        assertEquals("Updated Name", result.getNom());
    }

    @Test
    void shouldDeleteUser() {
        User user = new User(1L, "To Delete", new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).delete(user);
    }
}
