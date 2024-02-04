package ru.filmvibe.FilmVibe.exception.validation.user;

public class IncorrectLogin extends Exception {

    public IncorrectLogin() {
        super("Логин не должен содержать пробелы и содержать пробелы.");
    }
}
