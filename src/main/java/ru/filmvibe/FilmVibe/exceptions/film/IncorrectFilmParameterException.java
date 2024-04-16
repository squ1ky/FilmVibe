package ru.filmvibe.FilmVibe.exceptions.film;

public class IncorrectFilmParameterException extends RuntimeException {
    private final String parameterType;
    private final String value;

    public IncorrectFilmParameterException(String parameterType, String value) {
        super(String.format("Параметр - %s, значение - %s", parameterType, value));
        this.parameterType = parameterType;
        this.value = value;
    }
}
