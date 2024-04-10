package ru.filmvibe.FilmVibe.exceptions.film;

public class FilmAlreadyExistsException extends RuntimeException {

    public FilmAlreadyExistsException(String message) {
        super(message);
    }
}
