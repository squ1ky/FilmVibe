package ru.filmvibe.FilmVibe.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ru.filmvibe.FilmVibe.exception.validation.user.UserNotFoundException;
import ru.filmvibe.FilmVibe.model.User;
import ru.filmvibe.FilmVibe.storage.user.UserStorage;
import ru.filmvibe.FilmVibe.storage.user.InMemoryUserStorage;

import java.util.List;
import java.util.ArrayList;


@Service
public class UserService {

    @Autowired
    private final UserStorage userStorage = new InMemoryUserStorage();

    final List<Long> generalFriends = new ArrayList<>();

    public void addFriend(Long user1Id, Long user2Id) throws UserNotFoundException {
        User user1 = userStorage.getById(user1Id);
        User user2 = userStorage.getById(user2Id);
        user1.getFriends().add(user2Id);
        user2.getFriends().add(user1Id);
    }

    public void deleteFriend(Long user1Id, Long user2Id) throws UserNotFoundException {
        User user1 = userStorage.getById(user1Id);
        User user2 = userStorage.getById(user2Id);
        user1.getFriends().remove(user2Id);
        user2.getFriends().remove(user1Id);
    }

    public List<Long> getGeneralFriends(Long user1Id, Long user2Id) throws UserNotFoundException {
        User user1 = userStorage.getById(user1Id);
        User user2 = userStorage.getById(user2Id);
        for (Long friendId : user1.getFriends()) {
            if (user2.getFriends().contains(friendId)) {
                generalFriends.add(friendId);
            }
        }

        return generalFriends;
    }

    public User findUserById(Long id) throws UserNotFoundException {
        for (User user : userStorage.allUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }

        throw new UserNotFoundException(id.toString());
    }
}
