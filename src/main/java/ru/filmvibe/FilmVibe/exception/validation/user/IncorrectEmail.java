package ru.filmvibe.FilmVibe.exception.validation.user;

public class IncorrectEmail extends Exception {

    public IncorrectEmail() {
        super("Электронная почта не может быть пустой и должна содержать @.");
    }
}
