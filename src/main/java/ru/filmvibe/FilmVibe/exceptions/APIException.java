package ru.filmvibe.FilmVibe.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class APIException {
    private final HttpStatus httpStatus;
    private final String message;
    private final Throwable throwable;
    private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
}
