package ru.filmvibe.FilmVibe.service;

import ru.filmvibe.FilmVibe.model.Film;
import ru.filmvibe.FilmVibe.model.comparator.FilmComparator;
import ru.filmvibe.FilmVibe.storage.film.FilmStorage;
import ru.filmvibe.FilmVibe.storage.film.InMemoryFilmStorage;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class FilmService {

    @Autowired
    private final FilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    final Film[] top10ByLikes = new Film[10];

    public void addLike(Film film) {
        int likes = film.getLikes() + 1;
        film.setLikes(likes);
    }

    public void deleteLike(Film film) {
        int likes = film.getLikes() - 1;
        film.setLikes(likes);
    }

    public Film[] getTop10ByLikes() {
        List<Film> allFilms = inMemoryFilmStorage.allFilms();
        allFilms.sort(new FilmComparator());
        for (int i = 0; i < 10; i++) {
            top10ByLikes[i] = allFilms.get(i);
        }

        return top10ByLikes;
    }

}
