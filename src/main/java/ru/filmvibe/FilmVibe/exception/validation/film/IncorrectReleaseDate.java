package ru.filmvibe.FilmVibe.exception.validation.film;

public class IncorrectReleaseDate extends Exception {

    public IncorrectReleaseDate() {
        super("Дата резиза - не раньше 28 декабря 1895 года.");
    }
}
