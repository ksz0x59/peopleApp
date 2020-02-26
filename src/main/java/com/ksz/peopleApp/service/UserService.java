package com.ksz.peopleApp.service;

import com.ksz.peopleApp.dao.UserDao;
import com.ksz.peopleApp.model.Gender;
import com.ksz.peopleApp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;

    public UserService (UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.selectAllUsers();
        if (! gender.isPresent()) {
            return users;
        }
        try {
            Gender theGender = Gender.valueOf(gender.get().toUpperCase());
            return users.stream().filter(user -> user.getGender().equals(theGender))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new IllegalStateException("Invalid gender" , e);
        }
    }

    public Optional<User> getUser(UUID userId)
    {
        return userDao.selectUserByUserId(userId);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = userDao.selectUserByUserId(user.getUid());

        if (optionalUser.isPresent()){
            return userDao.updateUser(user);
        }

        return -1;
    }

    public int removeUser(UUID userId)
    {
        Optional<User> user = userDao.selectUserByUserId(userId);

        if (user.isPresent()){
            return userDao.deleteUserById(userId);
        }

        return -1;
    }

    public int addUser(User user)
    {
        UUID userId = UUID.randomUUID();
        return userDao.insertUser(userId, User.newUser(userId, user));
    }
}
