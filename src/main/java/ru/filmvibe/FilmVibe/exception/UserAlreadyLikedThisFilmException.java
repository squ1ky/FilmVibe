package ru.filmvibe.FilmVibe.exception;

public class UserAlreadyLikedThisFilmException extends Exception {

    public UserAlreadyLikedThisFilmException(String message) {
        super(message);
    }
}
