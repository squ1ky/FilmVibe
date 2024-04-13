package ru.filmvibe.FilmVibe.storage.film;

import ru.filmvibe.FilmVibe.exceptions.film.FilmAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.film.FilmNotFoundException;
import ru.filmvibe.FilmVibe.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film) throws FilmAlreadyExistsException;
    Film updateFilm(Film film, Long id);
    List<Film> allFilms();
    Film getById(Long id) throws FilmNotFoundException;
    String deleteById(Long id);

    Film makeFilm(ResultSet rs) throws SQLException;
}
