package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;
import java.time.Duration;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

import ru.filmvibe.FilmVibe.exception.IncorrectGenreException;
import ru.filmvibe.FilmVibe.exception.IncorrectMPAException;
import ru.filmvibe.FilmVibe.exception.validation.film.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Data
@Validated
public class Film {
    private static Long nextId = 1L;
    private Long id;

    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

    private String genre;
    private String MPA;

    private int likes = 0;
    private List<Long> likedById = new ArrayList<>();

    public Film(String name, String description, LocalDate releaseDate, Duration duration,
                String genre, String MPA)
        throws IncorrectReleaseDate, NegativeFilmDuration, IncorrectGenreException, IncorrectMPAException {
        this.id = nextId++;
        setName(name);
        setDescription(description);
        setReleaseDate(releaseDate);
        setDuration(duration);
        setGenre(genre);
        setMPA(MPA);
    }

    public Film() {
        this.id = nextId++;
        this.name = "No Name";
        this.description = null;
        this.releaseDate = null;
        this.duration = null;
        this.genre = null;
        this.MPA = "U";
    }

    public void setReleaseDate(LocalDate releaseDate) throws IncorrectReleaseDate {
        if (releaseDate.isAfter(LocalDate.of(1895, 12, 28))) {
            this.releaseDate = releaseDate;
        } else {
            throw new IncorrectReleaseDate();
        }
    }

    public void setDuration(Duration duration) throws NegativeFilmDuration {
        if (!duration.isNegative()) {
            this.duration = duration;
        } else {
            throw new NegativeFilmDuration();
        }
    }

    public void setGenre(String genre) throws IncorrectGenreException {
        if (genre.equals("Комедия") || genre.equals("Драма") ||
            genre.equals("Мультфильм") || genre.equals("Триллер") ||
            genre.equals("Документальный") || genre.equals("Боеввик")) {

            this.genre = genre;
        } else {
            throw new IncorrectGenreException(genre);
        }
    }

    public void setMPA(String MPA) throws IncorrectMPAException {
        if (MPA.equals("G") || MPA.equals("PG") ||
            MPA.equals("PG-13") || MPA.equals("R") ||
            MPA.equals("NC-17")) {
            this.MPA = MPA;
        }

        throw new IncorrectMPAException(MPA);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(name, film.getName()) &&
                Objects.equals(description, film.getDescription()) &&
                Objects.equals(releaseDate, film.getReleaseDate()) &&
                Objects.equals(duration, film.getDuration()) &&
                Objects.equals(likes, film.getLikes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, releaseDate, duration);
    }
}
