package ru.filmvibe.FilmVibe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.model.User;

import java.util.List;
import java.util.ArrayList;

@RestController
@Slf4j
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        users.add(user);
        log.info("POST-request /user");
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody User user) throws IncorrectBirthday {
        for (User currentUser : users) {
            if (currentUser.getId() == user.getId()) {
                currentUser.setEmail(user.getEmail());
                currentUser.setName(user.getName());
                currentUser.setLogin(user.getLogin());
                currentUser.setBirthday(user.getBirthday());
                log.info("PUT-request UPDATE /user");
                return currentUser;
            }
        }

        users.add(user);
        log.info("PUT-request CREATE /user");
        return user;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("GET-request /users");
        return users;
    }
}
