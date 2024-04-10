package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;
import java.time.Duration;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


@Data
@Validated
public class Film {

    @Getter
    @Setter
    private Long id = nextId;

    @Getter
    @Setter
    private static Long nextId = 1L;

    @NotBlank
    @Size(max = 64)
    private String name;

    @Size(max = 200)
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

    @Size(max = 64)
    private String genre;
    @Size(max = 10)
    private String mpa;

    private Long likes = 0L;
    private List<Long> likedById = new ArrayList<>();

    public Film() {
        setId(nextId++);
        this.name = null;
        this.description = null;
        this.releaseDate = null;
        this.duration = null;
        this.genre = null;
        this.mpa = null;
    }

    public Film(String name, String description, LocalDate releaseDate, Duration duration,
                String genre, String mpa) {
        setId(nextId++);
        setName(name);
        setDescription(description);
        setReleaseDate(releaseDate);
        setDuration(duration);
        setGenre(genre);
        setMPA(mpa);
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, Duration duration,
                String genre, String mpa) {
        setId(id);
        setName(name);
        setDescription(description);
        setReleaseDate(releaseDate);
        setDuration(duration);
        setGenre(genre);
        setMPA(mpa);
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isAfter(LocalDate.of(1895, 12, 28))) {
            this.releaseDate = releaseDate;
        } else {
            this.releaseDate = LocalDate.of(2000, 1, 1);
        }
    }

    public void setDuration(Duration duration) {
        if (!duration.isNegative()) {
            this.duration = duration;
        } else {
            this.duration = Duration.ofSeconds(0);
        }
    }

    public void setGenre(String genre) {
        if (genres.contains(genre)) {
            this.genre = genre;
        } else {
            this.genre = null;
        }
    }

    public void setMPA(String mpa) {
        if (mpaRatings.contains(mpa)) {
            this.mpa = mpa;
        } else {
            this.mpa = null;
        }
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

    private final ArrayList<String> mpaRatings = new ArrayList<>(Arrays.asList(
            "G",
            "PG",
            "PG-13",
            "R",
            "NC-17"
    ));
}
