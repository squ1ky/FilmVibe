package ru.filmvibe.FilmVibe.exceptions.film;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(String message) {
        super(message);
    }
}
