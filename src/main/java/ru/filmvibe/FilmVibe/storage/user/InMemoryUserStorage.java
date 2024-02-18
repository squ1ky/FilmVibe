package ru.filmvibe.FilmVibe.storage.user;

import org.springframework.stereotype.Component;

import ru.filmvibe.FilmVibe.exception.UserNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.model.User;

import java.util.List;
import java.util.ArrayList;


@Component
public class InMemoryUserStorage implements UserStorage {

    private final List<User> users = new ArrayList<>();

    @Override
    public User createUser(User user) {
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
    public User deleteUser(User user) {
        users.remove(user);
        return user;
    }


    @Override
    public List<User> allUsers() {
        return users;
    }
}
