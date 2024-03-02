package ru.filmvibe.FilmVibe.exception.user;

public class UserAlreadyLikedThisFilmException extends Exception {

    public UserAlreadyLikedThisFilmException(String message) {
        super(message);
    }
}
