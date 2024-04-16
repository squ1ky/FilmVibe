package ru.filmvibe.FilmVibe.exceptions.user;

public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException(String message) {
        super(message);
    }
}
