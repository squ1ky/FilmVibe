package ru.filmvibe.FilmVibe.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Valid;

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

    @PutMapping("/film/{id}")
    public Film updateFilm(@RequestBody Film film, @PathVariable Long id) {
        log.info("PUT-request CREATE /post " + film.getName());
        return filmStorage.updateFilm(film, id);
    }

    @GetMapping("/films")
    public List<Film> allFilms() {
        log.info("GET-request GET /posts");
        return filmStorage.allFilms();
    }

    @GetMapping("/film/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmStorage.getById(id);
    }

    @DeleteMapping("/film/{id}")
    public String deleteFilm(@PathVariable Long id) {
        return filmStorage.deleteById(id);
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

    @GetMapping("/films/popular")
    public List<Film> getTopByLikes(@RequestParam(required = false) Long count) {
        return filmService.getTopByLikes(count);
    }
}
