package ru.filmvibe.FilmVibe.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

import ru.filmvibe.FilmVibe.exception.*;
import ru.filmvibe.FilmVibe.exception.validation.film.IncorrectReleaseDate;
import ru.filmvibe.FilmVibe.exception.validation.film.NegativeFilmDuration;
import ru.filmvibe.FilmVibe.model.Film;
import ru.filmvibe.FilmVibe.service.FilmService;
import ru.filmvibe.FilmVibe.storage.film.FilmStorage;
import ru.filmvibe.FilmVibe.storage.film.InMemoryFilmStorage;

import java.util.List;


@RestController
@Slf4j
public class FilmController {

    @Autowired
    private final FilmStorage filmStorage = new InMemoryFilmStorage();
    @Autowired
    private FilmService filmService;

    @PostMapping("/film")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("POST-request CREATE /post " + film.getName());
        return filmStorage.addFilm(film);
    }

    @PutMapping("/film")
    public Film updateFilm(@Valid @RequestBody Film film) throws IncorrectReleaseDate,
            NegativeFilmDuration, FilmNotFoundException {
        log.info("PUT-request CREATE /post " + film.getName());
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> allFilms() {
        log.info("GET-request GET /posts");
        return filmStorage.allFilms();
    }

    @PutMapping("/films/{id}/like/{userId}")
    public String addLike(@PathVariable Long id, @PathVariable Long userId)
            throws FilmNotFoundException, UserAlreadyLikedThisFilmException {
        filmService.addLike(id, userId);
        return "Лайк поставлен.";
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public String deleteLike(@PathVariable Long id, @PathVariable Long userId)
            throws FilmNotFoundException, UserNotLikedThisFilmException {
        filmService.deleteLike(id, userId);
        return "Лайк убран с фильма.";
    }

    @GetMapping("/films/popular")
    public List<Film> getTopByLikes(@RequestParam(required = false) Long count)
            throws CountIsBiggerThanFilmsSizeException, IncorrectParameterException {
        return filmService.getTopByLikes(count);
    }
}
