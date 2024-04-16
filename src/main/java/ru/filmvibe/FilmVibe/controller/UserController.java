package ru.filmvibe.FilmVibe.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

//import ru.filmvibe.FilmVibe.exception.validation.user.UserNotFoundException;
//import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
//import ru.filmvibe.FilmVibe.exception.validation.user.UserAlreadyExistsException;
import ru.filmvibe.FilmVibe.model.User;
import ru.filmvibe.FilmVibe.storage.user.UserDbStorage;
import ru.filmvibe.FilmVibe.storage.user.UserStorage;
import ru.filmvibe.FilmVibe.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


@RestController
@Slf4j
public class UserController {

    @Autowired
    private final UserStorage userStorage;
    @Autowired
    final private UserService userService;


    @Autowired
    public UserController(UserDbStorage userDbStorage, UserService userService) {
        this.userStorage = userDbStorage;
        this.userService = userService;
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        log.info("POST-request CREATE /user " + user.getLogin());
        return userStorage.createUser(user);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        log.info("PUT-request update /user " + user.getLogin());
        return userStorage.updateUser(user, id);
    }

//    @DeleteMapping("/user/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        log.info("DELETE-request delete /user " + id);
//        return userStorage.deleteUserById(id);
//    }

    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("GET-request /users");
        return userStorage.allUsers();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public String addFriend(@PathVariable Long id, @PathVariable Long friendId) throws SQLException {
        userService.addFriend(id, friendId);
        return "Запрос в друзья был отправлен!";
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
        return "Друг удалён!";
    }

    @GetMapping("/users/{id}/friends")
    public List<Long> findFriends(@PathVariable Long id)  {
        return userStorage.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<Long> findGeneralFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getGeneralFriends(id, otherId);
    }

}
