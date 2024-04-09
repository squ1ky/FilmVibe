package ru.filmvibe.FilmVibe.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.filmvibe.FilmVibe.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalTime;
import java.util.List;

import java.time.LocalDate;
import java.time.Duration;


@Component
public class FilmDbStorage implements FilmStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {

        String sqlForFilms =
                """
                INSERT INTO Films (name, description)
                VALUES (?, ?)
                """;

        String sqlForFilmsInfo =
                """
                INSERT INTO Films_Info (genre, mpa, release_date, duration)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sqlForFilms,
                film.getName(),
                film.getDescription()
        );


        jdbcTemplate.update(sqlForFilmsInfo,
                film.getGenre(),
                film.getMpa(),
                film.getReleaseDate(),
                ConvertDurationToSqlTime(film.getDuration())
        );

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        // INSERT ID INTO JSON
        String sqlForFilms =
                """
                UPDATE Films
                SET name = ?,  description = ?
                WHERE id = ?
                """;

        String sqlForFilmsInfo =
                """
                UPDATE Films_Info
                SET genre = ?, mpa = ?, release_date = ?, duration = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sqlForFilms,
                film.getName(),
                film.getDescription(),
                film.getId()
        );

        jdbcTemplate.update(sqlForFilmsInfo,
                film.getGenre(),
                film.getMpa(),
                film.getReleaseDate(),
                ConvertDurationToSqlTime(film.getDuration()),
                film.getId()
        );

        return film;
    }

    @Override
    public List<Film> allFilms() {
        String sql =
                """
                SELECT Films.id, name, description, genre, mpa, release_date, duration
                FROM Films JOIN Films_Info ON Films.id = Films_Info.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    public Film getById(Long id) {
        String sql =
                """
                SELECT Films.id, name, description, genre, mpa, release_date, duration
                FROM Films JOIN Films_Info ON Films.id = Films_Info.id
                WHERE Films.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeFilm(rs), id);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Long id = rs.getLong("Films.id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        String genre = rs.getString("genre");
        String mpa = rs.getString("mpa");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();

        Duration duration = Duration.between(LocalTime.MIDNIGHT, rs.getTime("duration").toLocalTime());

        return new Film(id, name, description, releaseDate, duration, genre, mpa);
    }

    private java.sql.Time ConvertDurationToSqlTime(Duration duration) {
        LocalTime durationLT = LocalTime.MIDNIGHT.plus(duration);
        return java.sql.Time.valueOf(durationLT);
    }
}
