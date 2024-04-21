package ru.filmvibe.FilmVibe.storage.film;

import ru.filmvibe.FilmVibe.exceptions.film.FilmAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.film.FilmIdNotFoundException;
import ru.filmvibe.FilmVibe.exceptions.user.UserIdNotFoundException;
import ru.filmvibe.FilmVibe.model.Film;

import java.util.List;


public interface FilmStorage {
    Film addFilm(Film film) throws FilmAlreadyExistsException;
    Film updateFilm(Film film, Long id) throws FilmIdNotFoundException;
    List<Film> allFilms();
    List<Long> allFilmsId();
    Film getById(Long id) throws FilmIdNotFoundException, UserIdNotFoundException;
    String deleteById(Long id) throws FilmIdNotFoundException;
    boolean containsFilmId(Long id);
    boolean containsFilm(Film film);
}
