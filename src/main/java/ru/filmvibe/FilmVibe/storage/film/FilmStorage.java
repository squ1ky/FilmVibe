package ru.filmvibe.FilmVibe.storage.film;

import ru.filmvibe.FilmVibe.exceptions.film.FilmAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.film.FilmIdNotFoundException;
import ru.filmvibe.FilmVibe.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film) throws FilmAlreadyExistsException;
    Film updateFilm(Film film, Long id) throws FilmIdNotFoundException;
    List<Film> allFilms();
    Film getById(Long id) throws FilmIdNotFoundException;
    String deleteById(Long id) throws FilmIdNotFoundException;

    Film makeFilm(ResultSet rs) throws SQLException;
}
