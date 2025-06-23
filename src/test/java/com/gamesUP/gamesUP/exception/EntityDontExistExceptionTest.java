package com.gamesUP.gamesUP.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityDontExistExceptionTest {

    @Test
    void testDefaultConstructor() {
        EntityDontExistException exception = new EntityDontExistException();
        assertEquals("Entity not found", exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String message = "Custom entity not found message";
        EntityDontExistException exception = new EntityDontExistException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testIsInstanceOfRuntimeException() {
        EntityDontExistException exception = new EntityDontExistException();
        assertTrue(exception instanceof RuntimeException);
    }

}
