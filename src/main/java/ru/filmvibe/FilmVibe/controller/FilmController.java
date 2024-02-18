package ru.filmvibe.FilmVibe.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

import ru.filmvibe.FilmVibe.exception.FilmNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.film.IncorrectReleaseDate;
import ru.filmvibe.FilmVibe.exception.validation.film.NegativeFilmDuration;
import ru.filmvibe.FilmVibe.model.Film;
import ru.filmvibe.FilmVibe.storage.film.FilmStorage;
import ru.filmvibe.FilmVibe.storage.film.InMemoryFilmStorage;

import java.util.List;


@RestController
@Slf4j
public class FilmController {

    @Autowired
    private final FilmStorage filmStorage = new InMemoryFilmStorage();

    @PostMapping("/post")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("POST-request CREATE /post " + film.getName());
        return filmStorage.addFilm(film);
    }

    @PutMapping("/post")
    public Film updateFilm(@Valid @RequestBody Film film) throws IncorrectReleaseDate,
            NegativeFilmDuration, FilmNotFoundException {
        log.info("PUT-request CREATE /post " + film.getName());
        return filmStorage.updateFilm(film);
    }

    @DeleteMapping("/post")
    public Film deleteFilm(@Valid @RequestBody Film film) {
        log.info("DELETE-request DELETE /post " + film.getName());
        return filmStorage.deleteFilm(film);
    }

    @GetMapping("/posts")
    public List<Film> allFilms() {
        log.info("GET-request GET /posts");
        return filmStorage.allFilms();
    }
}
