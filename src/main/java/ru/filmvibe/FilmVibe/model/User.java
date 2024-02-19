package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import lombok.Data;

import ru.filmvibe.FilmVibe.exception.validation.user.*;

import java.util.List;
import java.util.ArrayList;


@Data
public class User {
    private int id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;

    private final List<User> friendList = new ArrayList<>();

    public void setName(String name) {
        if (name.isEmpty()) {
            this.name = this.login;
        }
    }

    public void setBirthday(LocalDate birthday) throws IncorrectBirthday {
        if (birthday.isBefore(LocalDate.now())) {
            this.birthday = birthday;
        } else {
            throw new IncorrectBirthday();
        }
    }

}
