package ru.filmvibe.FilmVibe.exception.validation.user;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
