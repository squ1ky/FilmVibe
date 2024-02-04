package ru.filmvibe.FilmVibe.exception.validation.film;

public class EmptyFilmName extends Exception {
    public EmptyFilmName() {
        super("Пустое название фильма.");
    }
}
