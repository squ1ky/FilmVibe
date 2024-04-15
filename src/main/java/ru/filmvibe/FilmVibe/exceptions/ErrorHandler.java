package ru.filmvibe.FilmVibe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.filmvibe.FilmVibe.exceptions.film.*;

@RestControllerAdvice
public class ErrorHandler {
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

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

    @ExceptionHandler(UserAlreadyLikedFilmException.class)
    public ResponseEntity<Object> handleUserAlreadyLikeFilmException(UserAlreadyLikedFilmException e) {
        String message = "Нельзя поставить лайк: Пользователь уже лайкнул этот фильм";
        return new ResponseEntity<>(new APIException(BAD_REQUEST, message + e.getMessage(), e), BAD_REQUEST);
    }

    @ExceptionHandler(UserNotLikedFilmException.class)
    public ResponseEntity<Object> handleUserAlreadyLikeFilmException(UserNotLikedFilmException e) {
        String message = "Нельзя убрать лайк: Пользователь не лайкал этот фильм";
        return new ResponseEntity<>(new APIException(BAD_REQUEST, message + e.getMessage(), e), BAD_REQUEST);
    }
}
