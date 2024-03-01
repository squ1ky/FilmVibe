package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import lombok.Data;

import ru.filmvibe.FilmVibe.exception.validation.user.*;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;


@Data
public class User {

    private static Long nextId = 1L;
    private Long id;

    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;

    private final Set<Long> friends = new HashSet<>();
    private final Set<Long> incomingFriendRequests = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday)
        throws IncorrectBirthday {
        this.id = nextId++;
        setEmail(email);
        setLogin(login);
        setName(name);
        setBirthday(birthday);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(email, user.getEmail()) &&
                Objects.equals(login, user.getLogin()) &&
                Objects.equals(name, user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, login, name);
    }

}
