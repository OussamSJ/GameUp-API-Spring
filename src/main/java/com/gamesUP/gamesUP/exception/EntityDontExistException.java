package com.gamesUP.gamesUP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class EntityDontExistException extends RuntimeException {

    public EntityDontExistException() {
        super("Entity not found");
    }
    public EntityDontExistException(String message) {
        super(message);
    }

}
