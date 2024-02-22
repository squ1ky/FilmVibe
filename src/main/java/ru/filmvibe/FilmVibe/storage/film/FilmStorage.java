package ru.filmvibe.FilmVibe.storage.film;

import ru.filmvibe.FilmVibe.exception.FilmNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.film.IncorrectReleaseDate;
import ru.filmvibe.FilmVibe.exception.validation.film.NegativeFilmDuration;
import ru.filmvibe.FilmVibe.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);
    Film updateFilm(Film film) throws NegativeFilmDuration, IncorrectReleaseDate, FilmNotFoundException;
    List<Film> allFilms();
    Film getById(Long id) throws FilmNotFoundException;
}
