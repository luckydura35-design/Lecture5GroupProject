package com.company.repositories.interfaces;

import com.company.models.User;

import java.util.List;

public interface IUserRepository {
    boolean createUser(User user);
    int logIn(String un, String pw);
    List<User> getAllUsers();
}
