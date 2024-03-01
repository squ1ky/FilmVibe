package ru.filmvibe.FilmVibe.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import ru.filmvibe.FilmVibe.exception.validation.user.UserNotFoundException;
import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.exception.validation.user.UserAlreadyExistsException;
import ru.filmvibe.FilmVibe.model.User;
import ru.filmvibe.FilmVibe.storage.user.UserStorage;
import ru.filmvibe.FilmVibe.service.UserService;
import ru.filmvibe.FilmVibe.storage.user.InMemoryUserStorage;

import java.util.List;
import java.util.ArrayList;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private final UserStorage userStorage = new InMemoryUserStorage();
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        log.info("POST-request CREATE /user " + user.getLogin());
        return userStorage.createUser(user);
    }

    // LOOK THIS
    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody User user) throws IncorrectBirthday, UserNotFoundException {
        log.info("PUT-request update /user " + user.getLogin());
        return userStorage.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("GET-request /users");
        return userStorage.allUsers();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public String addFriend(@PathVariable Long id, @PathVariable Long friendId) throws UserNotFoundException {
        userService.addFriend(id, friendId);
        return "Запрос в друзья был отправлен!";
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable Long id, @PathVariable Long friendId) throws UserNotFoundException {
        userService.deleteFriend(id, friendId);
        return "Друг удалён!";
    }

    @GetMapping("/users/{id}/friends")
    public List<Long> findFriends(@PathVariable Long id) throws UserNotFoundException {
        return new ArrayList<>(userStorage.getById(id).getFriends());
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<Long> findGeneralFriends(@PathVariable Long id, @PathVariable Long otherId)
            throws UserNotFoundException {
        return userService.getGeneralFriends(id, otherId);
    }

}
