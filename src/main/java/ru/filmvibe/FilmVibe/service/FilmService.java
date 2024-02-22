package ru.filmvibe.FilmVibe.service;

import ru.filmvibe.FilmVibe.exception.CountIsBiggerThanFilmsSizeException;
import ru.filmvibe.FilmVibe.exception.FilmNotFoundException;
import ru.filmvibe.FilmVibe.exception.UserNotLikedThisFilmException;
import ru.filmvibe.FilmVibe.model.Film;
import ru.filmvibe.FilmVibe.model.comparator.FilmComparator;
import ru.filmvibe.FilmVibe.storage.film.FilmStorage;
import ru.filmvibe.FilmVibe.storage.film.InMemoryFilmStorage;
import ru.filmvibe.FilmVibe.exception.UserAlreadyLikedThisFilmException;
import ru.filmvibe.FilmVibe.exception.IncorrectParameterException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;


@Service
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage = new InMemoryFilmStorage();
    final List<Film> topByLikes = new ArrayList<>();

    public void addLike(Long filmId, Long userId) throws FilmNotFoundException, UserAlreadyLikedThisFilmException {
        Film film = filmStorage.getById(filmId);
        if (film.getLikedById().contains(userId)) {
            throw new UserAlreadyLikedThisFilmException("Вы уже лайкнули этот фильм.");
        } else {
            int likes = film.getLikes() + 1;
            film.setLikes(likes);
            film.getLikedById().add(userId);
        }
    }

    public void deleteLike(Long filmId, Long userId) throws FilmNotFoundException, UserNotLikedThisFilmException {
        Film film = filmStorage.getById(filmId);
        if (!film.getLikedById().contains(userId)) {
            throw new UserNotLikedThisFilmException("Вы уже лайкнули этот фильм.");
        } else {
            int likes = film.getLikes() - 1;
            film.setLikes(likes);
            film.getLikedById().remove(userId);
        }
    }

    public List<Film> getTopByLikes(Long count) throws CountIsBiggerThanFilmsSizeException, IncorrectParameterException {
        topByLikes.clear();
        List<Film> allFilms = filmStorage.allFilms();

        if (count == null) {
            if (allFilms.size() > 10) {
                count = 10L;
            } else {
                count = (long) allFilms.size();
            }
        }

        if (count < 0) {
            throw new IncorrectParameterException(count.toString());
        }

        if (count > allFilms.size()) {
            throw new CountIsBiggerThanFilmsSizeException("count > allFilms.size()");
        }

        allFilms.sort(new FilmComparator());
        for (int i = 0; i < count; i++) {
            topByLikes.add(allFilms.get(i));
        }

        return topByLikes;
    }
}
