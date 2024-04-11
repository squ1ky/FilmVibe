package ru.filmvibe.FilmVibe.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.filmvibe.FilmVibe.exceptions.film.FilmAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.film.FilmNotFoundException;
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
    public Film addFilm(Film film) throws FilmAlreadyExistsException {

        if (ContainsFilm(film)) {
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
                INSERT INTO Film_Likes (likes_quantity)
                VALUES (?)
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

        jdbcTemplate.update(sqlForFilmLikes, 0);

        Film.setNextId(film.getId() + 1);

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

    @Override
    public String deleteById(Long id) {
        if (ContainsFilmId(id)) {

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

        return "Фильм не удален";
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
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

    private boolean ContainsFilmId(Long id) {
        String sql =
                """
                SELECT COUNT(*)
                FROM Films
                WHERE id = ?
                """;

        Long cnt = jdbcTemplate.queryForObject(sql, Long.class, id);

        if (cnt == null || cnt == 0L) {
            return false;
        }

        return true;
    }

    private boolean ContainsFilm(Film film) {
        String sql =
                """
                SELECT COUNT(*)
                FROM Films
                WHERE (name = ? AND description = ?)
                """;

        Long cnt = jdbcTemplate.queryForObject(sql, Long.class, film.getName(), film.getDescription());

        if (cnt == null || cnt == 0L) {
            return false;
        }

        return true;
    }
}
