package ru.filmvibe.FilmVibe.storage.user;

import ru.filmvibe.FilmVibe.exception.validation.user.UserAlreadyExistsException;
import ru.filmvibe.FilmVibe.exception.validation.user.UserNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.model.User;

import java.util.List;


public interface UserStorage {
    User createUser(User user) throws UserAlreadyExistsException;
    User updateUser(User user) throws IncorrectBirthday, UserNotFoundException;
    List<User> allUsers();
    User getById(Long id) throws UserNotFoundException;
}
