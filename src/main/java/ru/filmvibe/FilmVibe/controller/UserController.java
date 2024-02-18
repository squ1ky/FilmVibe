package ru.filmvibe.FilmVibe.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import ru.filmvibe.FilmVibe.exception.UserNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.model.User;
import ru.filmvibe.FilmVibe.storage.user.UserStorage;
import ru.filmvibe.FilmVibe.storage.user.InMemoryUserStorage;

import java.util.List;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private final UserStorage userStorage = new InMemoryUserStorage();

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        log.info("POST-request CREATE /user " + user.getLogin());
        return userStorage.createUser(user);
    }

    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody User user) throws IncorrectBirthday, UserNotFoundException {
        log.info("PUT-request update /user " + user.getLogin());
        return userStorage.updateUser(user);
    }

    @DeleteMapping("/user")
    public User deleteUser(@Valid @RequestBody User user) {
        log.info("DELETE-request delete /user " + user.getLogin());
        return userStorage.deleteUser(user);
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("GET-request /users");
        return userStorage.allUsers();
    }
}
