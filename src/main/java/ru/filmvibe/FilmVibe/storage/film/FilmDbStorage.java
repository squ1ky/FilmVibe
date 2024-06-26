package ru.filmvibe.FilmVibe.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.filmvibe.FilmVibe.exceptions.film.FilmAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.film.FilmIdNotFoundException;
import ru.filmvibe.FilmVibe.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Duration;


@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) throws FilmAlreadyExistsException {

        if (containsFilm(film)) {
            throw new FilmAlreadyExistsException(film.getName());
        }

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

        String sqlForFilmLikes =
                """
                INSERT INTO Film_Likes (id, likes_quantity)
                VALUES (?, 0)
                """;

        jdbcTemplate.update(sqlForFilms,
                film.getName(),
                film.getDescription()
        );

        jdbcTemplate.update(sqlForFilmsInfo,
                film.getGenre(),
                film.getMpa(),
                film.getReleaseDate(),
                convertDurationToSqlTime(film.getDuration())
        );

        Long id = getIdByName(film.getName());

        jdbcTemplate.update(sqlForFilmLikes, id);

        return film;
    }

    @Override
    public Film updateFilm(Film film, Long id) throws FilmIdNotFoundException {

        if (!containsFilmId(id)) {
            throw new FilmIdNotFoundException(id.toString());
        }

        film.setId(id);

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
                id
        );

        jdbcTemplate.update(sqlForFilmsInfo,
                film.getGenre(),
                film.getMpa(),
                film.getReleaseDate(),
                convertDurationToSqlTime(film.getDuration()),
                id
        );

        return film;
    }

    @Override
    public List<Film> allFilms() {
        String sql =
                """
                SELECT Films.id, name, description, genre, mpa, release_date, duration, likes_quantity
                FROM Films
                JOIN Films_Info ON Films.id = Films_Info.id
                JOIN Film_Likes ON Films.id = Film_Likes.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    public List<Long> allFilmsId() {
        String sql =
                """
                SELECT id
                FROM Films
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"));
    }

    public Film getById(Long id) throws FilmIdNotFoundException {

        if (!allFilmsId().contains(id)) {
            throw new FilmIdNotFoundException(id.toString());
        }

        String sql =
                """
                SELECT Films.id, name, description, genre, mpa, release_date, duration, likes_quantity
                FROM Films
                JOIN Films_Info ON Films.id = Films_Info.id
                JOIN Film_Likes ON Films.id = Film_Likes.id
                WHERE Films.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeFilm(rs), id);
    }

    private Long getIdByName(String name) {
        String sql =
                """
                SELECT id
                FROM Films
                WHERE name = ?
                """;

        return jdbcTemplate.queryForObject(sql, Long.class, name);
    }

    @Override
    public String deleteById(Long id) throws FilmIdNotFoundException {

        if (!containsFilmId(id)) {
            throw new FilmIdNotFoundException(id.toString());
        }

        String sqlForFilmsInfo =
                """
                DELETE FROM Films_Info
                WHERE id = ?
                """;

        String sqlForFilmLikedBy =
                """
                DELETE FROM Film_Liked_By
                WHERE film_id = ?
                """;

        String sqlForFilmLikes =
                """
                DELETE FROM Film_Likes
                WHERE id = ?
                """;

        String sqlForFilms =
                """
                DELETE FROM Films
                WHERE id = ?
                """;

        jdbcTemplate.update(sqlForFilmsInfo, id);
        jdbcTemplate.update(sqlForFilmLikedBy, id);
        jdbcTemplate.update(sqlForFilmLikes, id);
        jdbcTemplate.update(sqlForFilms, id);

        return "Фильм удален!";
    }

    public Film makeFilm(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        String genre = rs.getString("genre");
        String mpa = rs.getString("mpa");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Duration duration = Duration.between(LocalTime.MIDNIGHT, rs.getTime("duration").toLocalTime());
        Long likes = rs.getLong("likes_quantity");

        return new Film(id, name, description, releaseDate, duration, genre, mpa, likes);
    }

    private java.sql.Time convertDurationToSqlTime(Duration duration) {
        LocalTime durationLT = LocalTime.MIDNIGHT.plus(duration);
        return java.sql.Time.valueOf(durationLT);
    }

    @Override
    public boolean containsFilmId(Long id) {
        return allFilmsId().contains(id);
    }

    @Override
    public boolean containsFilm(Film film) {
        return allFilms().contains(film);
    }
}
