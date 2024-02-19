package ru.filmvibe.FilmVibe.service;

import org.springframework.stereotype.Service;

import ru.filmvibe.FilmVibe.model.User;

import java.util.List;
import java.util.ArrayList;


@Service
public class UserService {

    final List<User> generalFriends = new ArrayList<>();

    public void addFriend(User user1, User user2) {
        user1.getFriendList().add(user2);
        user2.getFriendList().add(user1);
    }

    public void deleteFriend(User user1, User user2) {
        user1.getFriendList().remove(user2);
        user2.getFriendList().remove(user1);
    }

    public List<User> getGeneralFriends(User user1, User user2) {
        for (User friend : user1.getFriendList()) {
            if (user2.getFriendList().contains(friend)) {
                generalFriends.add(friend);
            }
        }

        return generalFriends;
    }
}
