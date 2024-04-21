package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;
import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import ru.filmvibe.FilmVibe.exceptions.film.IncorrectFilmParameterException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Objects;


@Data
@Validated
public class Film {
    @Getter
    @Setter
    private Long id = 1L;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 1024)
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

    @Size(max = 64)
    private String genre;

    @Size(max = 10)
    private String mpa;
    private Long likes = 0L;

    public Film(Long id, String name, String description, LocalDate releaseDate, Duration duration,
                String genre, String mpa, Long likes) {
        setId(id);
        setName(name);
        setDescription(description);
        setReleaseDate(releaseDate);
        setDuration(duration);
        setGenre(genre);
        setMPA(mpa);
        setLikes(likes);
    }

    public void setReleaseDate(LocalDate releaseDate) throws IncorrectFilmParameterException {
        if (releaseDate.isAfter(LocalDate.of(1895, 12, 28))) {
            this.releaseDate = releaseDate;
        } else {
            throw new IncorrectFilmParameterException("Дата релиза фильма", releaseDate.toString());
        }
    }

    public void setDuration(Duration duration) throws IncorrectFilmParameterException {
        if (!duration.isNegative()) {
            this.duration = duration;
        } else {
            throw new IncorrectFilmParameterException("Длительность фильма", duration.toString());
        }
    }

    public void setGenre(String genre) throws IncorrectFilmParameterException {
        genre = firstLetterToUpper(genre);
        if (genres.contains(genre)) {
            this.genre = genre;
        } else {
            throw new IncorrectFilmParameterException("Жанр фильма", genre);
        }
    }

    public void setMPA(String mpa) throws IncorrectFilmParameterException {
        mpa = firstLetterToUpper(mpa);
        if (mpaRatings.contains(mpa)) {
            this.mpa = mpa;
        } else {
            throw new IncorrectFilmParameterException("MPA фильма", mpa);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;
        return Objects.equals(name, film.getName()) &&
                Objects.equals(description, film.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @JsonIgnore
    private final ArrayList<String> genres = new ArrayList<>(Arrays.asList(
            "Экшн",
            "Приключения",
            "Комедия",
            "Драма",
            "Ужасы",
            "Фантастика",
            "Триллер",
            "Мистика",
            "Криминал",
            "Мелодрама",
            "Вестерн",
            "Фэнтези",
            "Детектив",
            "Боевик",
            "Военный",
            "Исторический",
            "Мультфильм",
            "Музыкальный",
            "Фильм-нуар",
            "Документальный"
    ));

    @JsonIgnore
    private final ArrayList<String> mpaRatings = new ArrayList<>(Arrays.asList(
            "G",
            "PG",
            "PG-13",
            "R",
            "NC-17"
    ));

    private String firstLetterToUpper(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
