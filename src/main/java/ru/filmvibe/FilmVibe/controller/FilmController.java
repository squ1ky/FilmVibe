package ru.filmvibe.FilmVibe.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

//import ru.filmvibe.FilmVibe.exception.film.CountIsBiggerThanFilmsSizeException;
//import ru.filmvibe.FilmVibe.exception.user.UserAlreadyLikedThisFilmException;
//import ru.filmvibe.FilmVibe.exception.user.UserNotLikedThisFilmException;
//import ru.filmvibe.FilmVibe.exception.validation.film.FilmAlreadyExistsException;
//import ru.filmvibe.FilmVibe.exception.validation.film.FilmNotFoundException;
//import ru.filmvibe.FilmVibe.exception.validation.film.IncorrectReleaseDate;
//import ru.filmvibe.FilmVibe.exception.validation.film.NegativeFilmDuration;
import ru.filmvibe.FilmVibe.model.Film;
import ru.filmvibe.FilmVibe.service.FilmService;
import ru.filmvibe.FilmVibe.storage.film.FilmDbStorage;
import ru.filmvibe.FilmVibe.storage.film.FilmStorage;

import java.util.List;


@RestController
@Slf4j
public class FilmController {

    @Autowired
    private final FilmStorage filmStorage;
    @Autowired
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmDbStorage filmDbStorage, FilmService filmService) {
        this.filmStorage = filmDbStorage;
        this.filmService = filmService;
    }

    @PostMapping("/film")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("POST-request CREATE /post " + film.getName());
        return filmStorage.addFilm(film);
    }

    @PutMapping("/film")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("PUT-request CREATE /post " + film.getName());
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> allFilms() {
        log.info("GET-request GET /posts");
        return filmStorage.allFilms();
    }

    @GetMapping("/film/{id}")
    public Film getFilmById(Long id) {
        return filmStorage.getById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public String addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
        return "Лайк поставлен.";
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public String deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
        return "Лайк убран с фильма.";
    }

//    @GetMapping("/films/popular")
//    public List<Film> getTopByLikes(@RequestParam(required = false) Long count) {
//        return filmService.getTopByLikes(count);
//    }

}
