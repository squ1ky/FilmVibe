package ru.filmvibe.FilmVibe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.filmvibe.FilmVibe.exceptions.film.*;
import ru.filmvibe.FilmVibe.exceptions.user.AlreadyFriendsException;
import ru.filmvibe.FilmVibe.exceptions.user.NotFriendsException;
import ru.filmvibe.FilmVibe.exceptions.user.UserAlreadyExistsException;
import ru.filmvibe.FilmVibe.exceptions.user.UserIdNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final HttpStatus CONFLICT = HttpStatus.CONFLICT;

    @ExceptionHandler(FilmAlreadyExistsException.class)
    public ResponseEntity<Object> handleFilmAlreadyExistsException(FilmAlreadyExistsException e) {
        String message = String.format("Фильм с названием %s уже существует.", e.getMessage());
        return new ResponseEntity<>(new APIException(BAD_REQUEST, message, e), BAD_REQUEST);
    }

    @ExceptionHandler(FilmIdNotFoundException.class)
    public ResponseEntity<Object> handleFilmIdNotFoundException(FilmIdNotFoundException e) {
        String message = String.format("Фильм с id = %s не найден.", e.getMessage());
        return new ResponseEntity<>(new APIException(NOT_FOUND, message, e), NOT_FOUND);
    }

    @ExceptionHandler(IncorrectFilmParameterException.class)
    public ResponseEntity<Object> handleIncorrectFilmParameterException(IncorrectFilmParameterException e) {
        String message = "Неверное значение параметра. " + e.getMessage();
        return new ResponseEntity<>(new APIException(BAD_REQUEST, message, e), BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyLikedFilmException.class)
    public ResponseEntity<Object> handleUserAlreadyLikeFilmException(UserAlreadyLikedFilmException e) {
        String message = "Нельзя поставить лайк: Пользователь уже лайкнул этот фильм";
        return new ResponseEntity<>(new APIException(CONFLICT, message + e.getMessage(), e), CONFLICT);
    }

    @ExceptionHandler(UserNotLikedFilmException.class)
    public ResponseEntity<Object> handleUserAlreadyLikeFilmException(UserNotLikedFilmException e) {
        String message = "Нельзя убрать лайк: Пользователь не лайкал этот фильм";
        return new ResponseEntity<>(new APIException(CONFLICT, message + e.getMessage(), e), CONFLICT);
    }

    // USER SECTION

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        String message = String.format("Пользователь с почтой %s уже существует.", e.getMessage());
        return new ResponseEntity<>(new APIException(BAD_REQUEST, message, e), BAD_REQUEST);
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Object> handleUserIdNotFoundException(UserIdNotFoundException e) {
        String message = String.format("Пользователь с id = %s не найден.", e.getMessage());
        return new ResponseEntity<>(new APIException(NOT_FOUND, message, e), NOT_FOUND);
    }

    @ExceptionHandler(AlreadyFriendsException.class)
    public ResponseEntity<Object> handleAlreadyFriendsException(AlreadyFriendsException e) {
        String message = "Пользователи уже друзья.";
        return new ResponseEntity<>(new APIException(CONFLICT, message, e), CONFLICT);
    }

    @ExceptionHandler(NotFriendsException.class)
    public ResponseEntity<Object> handleNotFriendsException(NotFriendsException e) {
        String message = "Пользователи не друзья.";
        return new ResponseEntity<>(new APIException(CONFLICT, message, e), CONFLICT);
    }
}
