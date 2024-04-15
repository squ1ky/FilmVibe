package ru.filmvibe.FilmVibe.exceptions.film;

public class UserAlreadyLikedFilmException extends RuntimeException {

    public UserAlreadyLikedFilmException(String message) {
        super(message);
    }
}
