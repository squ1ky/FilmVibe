package ru.filmvibe.FilmVibe.service;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.filmvibe.FilmVibe.exceptions.film.FilmIdNotFoundException;
import ru.filmvibe.FilmVibe.exceptions.film.UserAlreadyLikedFilmException;
import ru.filmvibe.FilmVibe.exceptions.film.UserNotLikedFilmException;
import ru.filmvibe.FilmVibe.exceptions.user.UserIdNotFoundException;
import ru.filmvibe.FilmVibe.model.Film;
import ru.filmvibe.FilmVibe.storage.film.FilmStorage;
import ru.filmvibe.FilmVibe.storage.film.FilmDbStorage;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmService(FilmDbStorage filmDbStorage, JdbcTemplate jdbcTemplate) {
        filmStorage = filmDbStorage;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(Long filmId, Long userId) throws FilmIdNotFoundException,
                                                         UserIdNotFoundException,
                                                         UserAlreadyLikedFilmException {
        if (!filmStorage.containsFilmId(filmId)) {
            throw new FilmIdNotFoundException(filmId.toString());
        }

        if (!filmStorage.containsFilmId(userId)) {
            throw new UserIdNotFoundException(userId.toString());
        }

        if (getUsersIdLikedFilm(filmId).contains(userId)) {
            throw new UserAlreadyLikedFilmException(String.format(" (Film id = %s, User id = %s)", filmId, userId));
        } else {
            String sqlForFilmLikes =
                    """
                    UPDATE Film_Likes
                    SET likes_quantity = likes_quantity + 1
                    WHERE id = ?
                    """;

            String sqlForFilmLikedBy =
                    """
                    INSERT INTO Film_Liked_By (film_id, user_id)
                    VALUES (?, ?)
                    """;

            jdbcTemplate.update(sqlForFilmLikedBy, filmId, userId);
            jdbcTemplate.update(sqlForFilmLikes, filmId);
        }
    }

    public void deleteLike(Long filmId, Long userId) throws FilmIdNotFoundException,
                                                            UserNotLikedFilmException {

        if (!filmStorage.containsFilmId(filmId)) {
            throw new FilmIdNotFoundException(filmId.toString());
        }

        if (!filmStorage.containsFilmId(userId)) {
            throw new UserIdNotFoundException(userId.toString());
        }

        if (!getUsersIdLikedFilm(filmId).contains(userId)) {
            throw new UserNotLikedFilmException(String.format(" (Film id = %s, User id = %s)", filmId, userId));
        }

        String sqlForFilmLikes =
                """
                UPDATE Film_Likes
                SET likes_quantity = likes_quantity - 1
                WHERE id = ?
                """;

        String sqlForFilmLikedBy =
                """
                DELETE FROM Film_Liked_By
                WHERE (film_id = ? AND user_id = ?)
                """;

        jdbcTemplate.update(sqlForFilmLikedBy, filmId, userId);
        jdbcTemplate.update(sqlForFilmLikes, filmId);
    }

    private List<Long> getUsersIdLikedFilm(Long id) {
        String sql =
                """
                SELECT user_id
                FROM Film_Liked_By
                WHERE film_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("user_id"), id);
    }

    public List<Film> getTopByLikes(Long count) {
        return filmStorage.allFilms().stream()
                .sorted(Comparator.comparingLong(Film::getLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
