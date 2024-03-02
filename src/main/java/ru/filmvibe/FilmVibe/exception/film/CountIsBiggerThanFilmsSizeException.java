package ru.filmvibe.FilmVibe.exception.film;

public class CountIsBiggerThanFilmsSizeException extends RuntimeException {

    public CountIsBiggerThanFilmsSizeException(String message) {
        super(message);
    }
}
