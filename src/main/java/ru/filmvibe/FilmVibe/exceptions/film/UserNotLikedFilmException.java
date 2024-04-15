package ru.filmvibe.FilmVibe.exceptions.film;

public class UserNotLikedFilmException extends RuntimeException {

    public UserNotLikedFilmException(String message) {
        super(message);
    }
}
