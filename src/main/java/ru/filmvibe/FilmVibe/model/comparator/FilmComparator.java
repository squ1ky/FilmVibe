package ru.filmvibe.FilmVibe.model.comparator;

import ru.filmvibe.FilmVibe.model.Film;

import java.util.Comparator;

public class FilmComparator implements Comparator<Film> {

    @Override
    public int compare(Film film1, Film film2) {
        return (int) (film2.getLikes() - film1.getLikes());
    }

    //MAKE INT -> LONG
}
