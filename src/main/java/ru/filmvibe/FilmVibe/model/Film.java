package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;
import java.time.Duration;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

import ru.filmvibe.FilmVibe.exception.validation.film.*;

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

    private int likes = 0;

    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film() {
        this.id = nextId++;
        this.name = "No Name";
        this.description = null;
        this.releaseDate = null;
        this.duration = null;
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

}
