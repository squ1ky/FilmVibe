package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;
import java.time.Duration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

import ru.filmvibe.FilmVibe.exception.validation.film.*;

@Data
public class Film {
    private int id;

    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

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
