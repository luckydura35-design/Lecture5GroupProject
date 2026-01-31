package com.company.controllers;

import com.company.models.User;
import com.company.controllers.interfaces.IUserController;
import com.company.repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) { // Dependency Injection
        this.repo = repo;
    }

    @Override
    public boolean createUser(String username, String email, String phone, String password, String first_name, String last_name) {
        User user = new User(username, email, phone, password, first_name, last_name);

        boolean created = repo.createUser(user);

        return (created);
    }

    public int logIn(String un, String pw) {
        int result = repo.logIn(un, pw);
        return (result);
    }

    public String getAllUsers() {
        List<User> users = repo.getAllUsers();

        StringBuilder response = new StringBuilder();
        for (User user : users) {
            response.append(user.toString()).append("\n");
        }

        return response.toString();
    }
}
