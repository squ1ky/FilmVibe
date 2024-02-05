package ru.filmvibe.FilmVibe.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ru.filmvibe.FilmVibe.exception.validation.film.IncorrectReleaseDate;
import ru.filmvibe.FilmVibe.exception.validation.film.NegativeFilmDuration;
import ru.filmvibe.FilmVibe.model.Film;

import java.util.List;
import java.util.ArrayList;

@RestController
@Slf4j
public class FilmController {

    private List<Film> films = new ArrayList<>();

    @PostMapping("/post")
    public Film addFilm(@Valid @RequestBody Film film) {
        films.add(film);
        log.info("POST-request /post");
        return film;
    }

    @PutMapping("/post")
    public Film updateFilm(@Valid @RequestBody Film film) throws IncorrectReleaseDate, NegativeFilmDuration {
        for (Film currentFilm : films) {
            if (currentFilm.getId() == film.getId()) {
                currentFilm.setName(film.getName());
                currentFilm.setDescription(film.getDescription());
                currentFilm.setReleaseDate(film.getReleaseDate());
                currentFilm.setDuration(film.getDuration());
                log.info("PUT-request UPDATE /post");
                return currentFilm;
            }
        }

        films.add(film);
        log.info("PUT-request CREATE /post");
        return film;
    }

    @GetMapping("/posts")
    public List<Film> allFilms() {
        log.info("GET-request GET /posts");
        return films;
    }
}
