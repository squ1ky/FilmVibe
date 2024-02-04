package ru.filmvibe.FilmVibe.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ru.filmvibe.FilmVibe.exception.validation.user.IncorrectBirthday;
import ru.filmvibe.FilmVibe.model.User;

import java.util.List;
import java.util.ArrayList;

@RestController
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        users.add(user);
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
                return currentUser;
            }
        }

        users.add(user);
        return user;
    }
}
