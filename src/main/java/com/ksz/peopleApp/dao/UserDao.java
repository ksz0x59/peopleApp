package com.ksz.peopleApp.dao;

import com.ksz.peopleApp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao
{
    public List<User> selectAllUsers ();

    public Optional<User> selectUserByUserId (UUID userId);

    public int updateUser(User user);

    public int deleteUserById(UUID userId);

    public int insertUser(UUID userId, User user);
}
