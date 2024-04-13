package ru.filmvibe.FilmVibe.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

//import ru.filmvibe.FilmVibe.exception.validation.user.UserNotFoundException;
import ru.filmvibe.FilmVibe.model.User;
import ru.filmvibe.FilmVibe.storage.user.UserDbStorage;
import ru.filmvibe.FilmVibe.storage.user.UserStorage;

import java.util.List;
import java.util.ArrayList;


@Service
public class UserService {

    @Autowired
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserDbStorage userDbStorage) {
        userStorage = userDbStorage;
    }

    public void addFriend(Long id, Long friendId) {
        userStorage.addFriend(id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    public List<Long> getGeneralFriends(Long user1Id, Long user2Id) {
        List<Long> friendsOfUser1 = userStorage.getFriends(user1Id);
        List<Long> friendsOfUser2 = userStorage.getFriends(user2Id);

        List<Long> result = new ArrayList<>();

        for (Long id : friendsOfUser1) {
            if (friendsOfUser2.contains(id)) {
                result.add(id);
            }
        }

        return result;
    }

    public User findUserById(Long id) {
        return userStorage.getById(id);
//        throw new UserNotFoundException(id.toString());
    }
}
