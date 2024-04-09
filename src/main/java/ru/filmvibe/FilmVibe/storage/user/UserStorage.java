package ru.filmvibe.FilmVibe.storage.user;

import ru.filmvibe.FilmVibe.model.User;

import java.util.List;


public interface UserStorage {
    User createUser(User user);
    User updateUser(User user);
    List<User> allUsers();
    User getById(Long id);

    void addFriend(Long userId, Long friendId);
    void deleteFriend(Long userId, Long friendId);
}
