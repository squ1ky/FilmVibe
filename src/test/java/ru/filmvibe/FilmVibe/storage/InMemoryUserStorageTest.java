package ru.filmvibe.FilmVibe.storage;

import ru.filmvibe.FilmVibe.model.User;
import ru.filmvibe.FilmVibe.storage.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class InMemoryUserStorageTest {

    @Test
    public void mustBeEmpty() {

        UserStorage userStorage = new InMemoryUserStorage();

        User firstUser = new User("alsdl23@mail.ru", "alsdl23", "alsdl", LocalDate.now());
        User secondUser = new User("gkslm23@mail.ru", "gkslm23", "gkslm", LocalDate.now());

        userStorage.createUser(firstUser);
        userStorage.createUser(secondUser);

        userStorage.deleteUser(firstUser);
        userStorage.deleteUser(secondUser);

        Assertions.assertTrue(userStorage.allUsers().isEmpty());
    }
}
