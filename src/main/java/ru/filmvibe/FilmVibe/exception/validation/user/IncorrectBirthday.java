package ru.filmvibe.FilmVibe.exception.validation.user;

public class IncorrectBirthday extends Exception {

    public IncorrectBirthday() {
        super("Как вы попали в будущее?");
    }
}
