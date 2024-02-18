package ru.filmvibe.FilmVibe.storage.user;

import ru.filmvibe.FilmVibe.exception.UserNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.model.User;

import java.util.List;


public interface UserStorage {
    User createUser(User user);
    User updateUser(User user) throws IncorrectBirthday, UserNotFoundException;
    User deleteUser(User user);
    List<User> allUsers();
}
