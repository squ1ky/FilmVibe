package ru.filmvibe.FilmVibe.storage.user;

import ru.filmvibe.FilmVibe.exceptions.user.AlreadyFriendsException;
import ru.filmvibe.FilmVibe.exceptions.user.NotFriendsException;
import ru.filmvibe.FilmVibe.exceptions.user.UserAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.user.UserIdNotFoundException;
import ru.filmvibe.FilmVibe.model.User;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.time.LocalDate;

import java.util.List;


@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {

        if (containsUser(user)) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        String sql =
                """
                INSERT INTO Users (email, login, name, birthday)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );

        return user;
    }

    @Override
    public User updateUser(User user, Long id) throws UserIdNotFoundException {

        throwNotFoundIfUserNotExists(id);

        user.setId(id);

        String sql =
                """
                UPDATE Users
                SET email = ?, login = ?, name = ?, birthday = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        return user;
    }

    @Override
    public String deleteUserById(Long id) {

        throwNotFoundIfUserNotExists(id);

        String sqlForFilmLikedBy =
                """
                DELETE FROM Film_Liked_By CASCADE
                WHERE user_id = ?
                """;

        String sqlForFilmLikes =
                """
                UPDATE Film_Likes
                SET likes_quantity = likes_quantity - 1
                WHERE id = ?
                """;

        String sqlForUsers =
                """
                DELETE FROM Users CASCADE
                WHERE id = ?
                """;

        String sqlForFriends =
                """
                DELETE FROM Friends
                WHERE (user_id = ? OR friend_id = ?)
                """;

        for (Long filmId : getLikedFilms(id)) {
            jdbcTemplate.update(sqlForFilmLikes, filmId);
        }

        jdbcTemplate.update(sqlForFilmLikedBy, id);
        jdbcTemplate.update(sqlForFriends, id, id);
        jdbcTemplate.update(sqlForUsers, id);

        return "Пользователь удален!";
    }

    @Override
    public List<User> allUsers() {
        String sql = "SELECT * FROM Users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    public List<Long> allUsersId() {
        String sql =
                """
                SELECT id
                FROM Users
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"));
    }

    @Override
    public User getById(Long id) throws UserIdNotFoundException {

        throwNotFoundIfUserNotExists(id);

        String sql =
                """
                SELECT * FROM Users
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeUser(rs), id);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return new User(id, email, login, name, birthday);
    }

    @Transactional
    @Override
    public void addFriend(Long userId, Long friendId) throws UserIdNotFoundException,
                                                             AlreadyFriendsException {

        throwNotFoundIfUsersNotExist(userId, friendId);

        if (getFriends(userId).contains(friendId)) {
            throw new AlreadyFriendsException("");
        }

        String sqlForUpdateFriendStatus =
                """
                UPDATE Friends
                SET is_accepted = ?
                WHERE user_id = ? AND friend_id = ?
                """;

        String sqlForNewFriend =
                """
                INSERT INTO FRIENDS (user_id, friend_id, is_accepted)
                VALUES (?, ?, ?)
                """;

        if (isFriendRequestExists(userId, friendId) == 0L) {
            jdbcTemplate.update(sqlForNewFriend, userId, friendId, false);
            jdbcTemplate.update(sqlForNewFriend, friendId, userId, false);
        } else {
            if (isFriendRequestExists(friendId, userId) != 0L) {
                jdbcTemplate.update(sqlForUpdateFriendStatus, true, friendId, userId);
                jdbcTemplate.update(sqlForUpdateFriendStatus, true, userId, friendId);
            }
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) throws UserIdNotFoundException,
                                                                NotFriendsException {

        throwNotFoundIfUsersNotExist(userId, friendId);

        if (!getFriends(userId).contains(friendId)) {
            throw new NotFriendsException("");
        }

        String sql =
                """
                DELETE FROM Friends WHERE (user_id = ? AND friend_id = ?);
                DELETE FROM Friends WHERE (friend_id = ? AND user_id = ?);
                """;

        jdbcTemplate.update(sql, userId, friendId, userId, friendId);
    }

    @Override
    public List<Long> getFriends(Long id) throws UserIdNotFoundException {

        throwNotFoundIfUserNotExists(id);

        String sql =
                """
                SELECT friend_id
                FROM Friends
                WHERE (user_id = ? AND is_accepted = true)
                """;

        return jdbcTemplate.queryForList(sql, Long.class, id);
    }

    private Long isFriendRequestExists(Long userId, Long friendId) {
        String sql =
                """
                SELECT COUNT(*)
                FROM Friends
                WHERE (user_id = ? AND friend_id = ?)
                """;

        return jdbcTemplate.queryForObject(sql, Long.class, userId, friendId);
    }

    @Override
    public boolean containsUserId(Long id) {
        return allUsersId().contains(id);
    }

    @Override
    public boolean containsUser(User user) {
        return allUsers().contains(user);
    }

    private void throwNotFoundIfUserNotExists(Long id) throws UserIdNotFoundException {
        if (!containsUserId(id)) throw new UserIdNotFoundException(id.toString());
    }

    private void throwNotFoundIfUsersNotExist(Long user1Id, Long user2Id) throws UserIdNotFoundException {

        if (!containsUserId(user1Id)) {
            throw new UserIdNotFoundException(user1Id.toString());
        }

        if (!containsUserId(user2Id)) {
            throw new UserIdNotFoundException(user2Id.toString());
        }
    }

    private List<Long> getLikedFilms(Long id) {
        String sql =
                """
                SELECT film_id
                FROM Film_Liked_By
                WHERE user_id = ?
                """;

        return jdbcTemplate.queryForList(sql, Long.class, id);
    }
}
