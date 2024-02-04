package ru.filmvibe.FilmVibe.exception.validation.film;

public class NegativeFilmDuration extends Exception {

    public NegativeFilmDuration() {
        super("Длительность фильма должна быть положительной.");
    }
}
