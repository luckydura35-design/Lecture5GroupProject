package com.company.controllers;

import com.company.models.EntityFactory;
import com.company.models.User;
import com.company.controllers.interfaces.IUserController;
import com.company.repositories.interfaces.IUserRepository;


import java.util.List;
import java.util.stream.Collectors;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) { // Dependency Injection
        this.repo = repo;
    }

    @Override
    public boolean createUser(String username, String email, String phone, String password, String first_name, String last_name) {
        User user = EntityFactory.createUser(username, email, phone, password, first_name, last_name);

        boolean created = repo.createUser(user);

        return (created);
    }

    public int logIn(String un, String pw) {
        int result = repo.logIn(un, pw);
        return (result);
    }

    public String getAllUsers() {
        List<User> users = repo.getAllUsers();

        return users.stream()
                .map(User::toString)
                .collect(Collectors.joining("\n"));
    }

    public boolean updateUserField(int userId, String columnName, String newValue) {
        return repo.updateUserField(userId, columnName, newValue);
    }

    public User findUser(String columnName, String targetValue) {
        return repo.findUser(columnName, targetValue);
    }

    public boolean banUser(int targetUserId) {
        return repo.banUser(targetUserId);
    }
}