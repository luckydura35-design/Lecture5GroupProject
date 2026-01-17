package com.company.controllers.interfaces;

public interface IUserController {
    boolean createUser(String username, String email, String phone, String password, String first_name, String last_name);
    int logIn(String un, String pw);
    String getAllUsers();
}
