package ru.filmvibe.FilmVibe.storage.user;

import ru.filmvibe.FilmVibe.exceptions.user.AlreadyFriendsException;
import ru.filmvibe.FilmVibe.exceptions.user.NotFriendsException;
import ru.filmvibe.FilmVibe.exceptions.user.UserAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.user.UserIdNotFoundException;
import ru.filmvibe.FilmVibe.model.User;

import java.util.List;


public interface UserStorage {
    User createUser(User user) throws UserAlreadyExistsException;
    User updateUser(User user, Long id) throws UserIdNotFoundException;
    String deleteUserById(Long id) throws UserIdNotFoundException;
    List<User> allUsers();
    User getById(Long id) throws UserIdNotFoundException;

    void addFriend(Long userId, Long friendId) throws UserIdNotFoundException, AlreadyFriendsException;
    void deleteFriend(Long userId, Long friendId) throws UserIdNotFoundException, NotFriendsException;
    List<Long> getFriends(Long id) throws UserIdNotFoundException;
    boolean containsUser(User user);
    boolean containsUserId(Long id);
}
