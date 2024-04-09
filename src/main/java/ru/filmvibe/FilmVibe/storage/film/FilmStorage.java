package ru.filmvibe.FilmVibe.storage.film;

import ru.filmvibe.FilmVibe.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);
    Film updateFilm(Film film);
    List<Film> allFilms();
    Film getById(Long id);
}
