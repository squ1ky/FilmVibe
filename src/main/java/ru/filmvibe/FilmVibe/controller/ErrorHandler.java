package ru.filmvibe.FilmVibe.controller;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.filmvibe.FilmVibe.exception.*;
import ru.filmvibe.FilmVibe.exception.film.CountIsBiggerThanFilmsSizeException;
import ru.filmvibe.FilmVibe.exception.film.IncorrectGenreException;
import ru.filmvibe.FilmVibe.exception.film.IncorrectMPAException;
import ru.filmvibe.FilmVibe.exception.user.UserAlreadyLikedThisFilmException;
import ru.filmvibe.FilmVibe.exception.user.UserNotLikedThisFilmException;
import ru.filmvibe.FilmVibe.exception.validation.film.*;
import ru.filmvibe.FilmVibe.exception.validation.user.*;


@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(final UserNotFoundException e) {
        return "Пользователь " + e.getMessage() + " не найден.";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(final UserAlreadyExistsException e) {
        return "Пользователь " + e.getMessage() + " уже существует.";
    }

    @ExceptionHandler(UserAlreadyLikedThisFilmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyLikeThisFilmException() {
        return "Пользователь уже лайкнул этот фильм.";
    }

    @ExceptionHandler(UserNotLikedThisFilmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserNotLikeThisFilmException(final UserNotLikedThisFilmException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IncorrectBirthday.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIncorrectBirthdayException() {
        return "Неправильная дата рождения!";
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFilmNotFoundException() {
        return "Фильм не найден.";
    }

    @ExceptionHandler(FilmAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleFilmAlreadyExistsException(final FilmAlreadyExistsException e) {
        return "Фильм " + e.getMessage() + " уже существует.";
    }

    @ExceptionHandler(NegativeFilmDuration.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNegativeFilmDuration() {
        return "Неправильная длительность фильма.";
    }

    @ExceptionHandler(IncorrectReleaseDate.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIncorrectReleaseDate() {
        return "Неправильная дата релиза.";
    }

    @ExceptionHandler(CountIsBiggerThanFilmsSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCountIsBiggerThanFilmsSizeException(final CountIsBiggerThanFilmsSizeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IncorrectParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIncorrectParameterException(final IncorrectParameterException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IncorrectGenreException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIncorrectGenreException(final IncorrectGenreException e) {
        return "Неправильное название жанра: " + e.getMessage();
    }

    @ExceptionHandler(IncorrectMPAException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIncorrectMPAException(final IncorrectMPAException e) {
        return "Неправильный формат MPA (Motion Picture Association): " + e.getMessage();
    }
}
