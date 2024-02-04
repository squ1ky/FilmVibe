package ru.filmvibe.FilmVibe.exception.validation.film;

public class MaxFilmDescriptionLength extends Exception {

    public MaxFilmDescriptionLength() {
        super("Максимальная длина описания фильма - 200 слов.");
    }
}
