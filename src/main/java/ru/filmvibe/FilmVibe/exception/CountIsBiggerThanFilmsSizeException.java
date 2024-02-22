package ru.filmvibe.FilmVibe.exception;

public class CountIsBiggerThanFilmsSizeException extends RuntimeException {

    public CountIsBiggerThanFilmsSizeException(String message) {
        super(message);
    }
}
