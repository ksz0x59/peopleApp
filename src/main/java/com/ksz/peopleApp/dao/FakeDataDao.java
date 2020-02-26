package com.ksz.peopleApp.dao;

import com.ksz.peopleApp.model.Gender;
import com.ksz.peopleApp.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FakeDataDao implements UserDao {

    private static Map<UUID, User> database;

    static {
        database = new ConcurrentHashMap<>();
        UUID joeUserId = UUID.randomUUID();
        database.put(joeUserId, new User(joeUserId, "Joe", "Johnson", Gender.MALE, 22,
                "joe.johnson@gmail.com"));
    }

    @Override
    public List<User> selectAllUsers ()
    {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserId (UUID userId)
    {
        return Optional.ofNullable(database.get(userId));
    }

    @Override
    public int updateUser(User user)
    {
        database.put(user.getUid(), user);
        return 1;
    }

    @Override
    public int deleteUserById(UUID userId)
    {
        database.remove(userId);
        return 1;
    }

    @Override
    public int insertUser(UUID userId, User user)
    {
        database.put(userId, user);
        return 1;
    }



}
