package ru.filmvibe.FilmVibe.storage.film;

import org.springframework.stereotype.Component;

import ru.filmvibe.FilmVibe.exception.FilmNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.film.IncorrectReleaseDate;
import ru.filmvibe.FilmVibe.exception.validation.film.NegativeFilmDuration;
import ru.filmvibe.FilmVibe.model.Film;

import java.util.List;
import java.util.ArrayList;


@Component
public class InMemoryFilmStorage implements FilmStorage {

    final List<Film> films = new ArrayList<>();

    @Override
    public Film addFilm(Film film) {
        films.add(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) throws NegativeFilmDuration, IncorrectReleaseDate, FilmNotFoundException {
        for (Film currentFilm : films) {
            if (currentFilm.getName().equals(film.getName())) {
                currentFilm.setDescription(film.getDescription());
                currentFilm.setDuration(film.getDuration());
                currentFilm.setReleaseDate(film.getReleaseDate());
                return currentFilm;
            }
        }

        throw new FilmNotFoundException(film.getName());
    }

    @Override
    public List<Film> allFilms() {
        return films;
    }

    @Override
    public Film getById(Long id) throws FilmNotFoundException {
        for (Film film : films) {
            if (film.getId().equals(id)) {
                return film;
            }
        }

        throw new FilmNotFoundException(id.toString());
    }
}
