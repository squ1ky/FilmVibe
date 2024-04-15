package ru.filmvibe.FilmVibe.exceptions.film;

public class FilmIdNotFoundException extends RuntimeException {

    public FilmIdNotFoundException(String message) {
        super(message);
    }
}
