package ru.filmvibe.FilmVibe.service;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.filmvibe.FilmVibe.model.Film;
import ru.filmvibe.FilmVibe.model.comparator.FilmComparator;
import ru.filmvibe.FilmVibe.storage.film.FilmStorage;
import ru.filmvibe.FilmVibe.storage.film.FilmDbStorage;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;


@Service
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmService(FilmDbStorage filmDbStorage, JdbcTemplate jdbcTemplate) {
        filmStorage = filmDbStorage;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(Long filmId, Long userId) {
        if (getUsersIdLikedFilm(filmId).contains(userId)) {
            //throw new UserAlreadyLikedThisFilmException("Пользователь уже лайкнул этот фильм.");
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

    public void deleteLike(Long filmId, Long userId) {
        if (getUsersIdLikedFilm(filmId).contains(userId)) {
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
        } else {
            // throw exception ...
        }
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
        String sql =
                """
                SELECT
                Films.id as id,
                name,
                description,
                genre,
                mpa,
                release_date,
                duration,
                likes_quantity,
                FROM Film_Likes
                JOIN Films ON Film_Likes.id = Films.id
                JOIN Films_Info ON Film_Likes.id = Films_Info.id
                ORDER BY likes_quantity DESC
                LIMIT(?)
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> filmStorage.makeFilm(rs) , count);
    }


//    public List<Film> getTopByLikes(Long count) {
//        topByLikes.clear();
//        List<Film> allFilms = filmStorage.allFilms();
//
//        if (count == null) {
//            if (allFilms.size() > 10) {
//                count = 10L;
//            } else {
//                count = (long) allFilms.size();
//            }
//        }
//
//        if (count < 0) {
//            throw new IncorrectParameterException(count.toString());
//        }
//
//        if (count > allFilms.size()) {
//            throw new CountIsBiggerThanFilmsSizeException("count > allFilms.size()");
//        }
//
//        allFilms.sort(new FilmComparator());
//        for (int i = 0; i < count; i++) {
//            topByLikes.add(allFilms.get(i));
//        }
//
//        return topByLikes;
// }
}
