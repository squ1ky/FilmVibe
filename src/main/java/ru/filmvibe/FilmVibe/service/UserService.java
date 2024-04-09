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

    final List<Long> generalFriends = new ArrayList<>();

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

//    public List<Long> getGeneralFriends(Long user1Id, Long user2Id) {
//        User user1 = userStorage.getById(user1Id);
//        User user2 = userStorage.getById(user2Id);
//        for (Long friendId : user1.getFriends()) {
//            if (user2.getFriends().contains(friendId)) {
//                generalFriends.add(friendId);
//            }
//        }
//
//        return generalFriends;
//    }

    public User findUserById(Long id) {
        return userStorage.getById(id);
//        throw new UserNotFoundException(id.toString());
    }
}
