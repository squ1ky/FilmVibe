package ru.filmvibe.FilmVibe.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import ru.filmvibe.FilmVibe.exceptions.user.IncorrectBirthdayException;

import java.util.Objects;


@Data
public class User {
    @Setter
    @Getter
    private Long id;

    @Email
    private String email;

    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        setId(id);
        setEmail(email);
        setLogin(login);
        setName(name);
        setBirthday(birthday);
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            this.name = this.login;
        } else {
            this.name = name;
        }
    }

    public void setBirthday(LocalDate birthday) throws IncorrectBirthdayException {
        if (birthday.isBefore(LocalDate.now())) {
            this.birthday = birthday;
        } else {
            throw new IncorrectBirthdayException(birthday.toString());
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
