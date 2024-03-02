package ru.filmvibe.FilmVibe.storage.user;

import org.springframework.stereotype.Component;

import ru.filmvibe.FilmVibe.exception.validation.user.UserNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.user.UserAlreadyExistsException;
import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.model.User;

import java.util.List;
import java.util.ArrayList;


@Component
public class InMemoryUserStorage implements UserStorage {

    private final List<User> users = new ArrayList<>();

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        if (users.contains(user)) {
            User.setNextId(User.getNextId() - 1);
            throw new UserAlreadyExistsException(user.getLogin());
        }

        users.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) throws IncorrectBirthday, UserNotFoundException {
        for (User currentUser : users) {
            if (currentUser.getLogin().equals(user.getLogin())) {
                currentUser.setName(user.getName());
                currentUser.setEmail(user.getEmail());
                currentUser.setBirthday(user.getBirthday());
            }
        }

        throw new UserNotFoundException(user.getName());
    }

    @Override
    public List<User> allUsers() {
        return users;
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }

        throw new UserNotFoundException(id.toString());
    }
}
