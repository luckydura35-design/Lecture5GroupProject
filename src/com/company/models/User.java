package com.company.models;

public class User {
    private int id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String first_name;
    private String last_name;

    // Пустой конструктор
    public User() {
    }

    // Конструктор без ID (удобно для создания нового юзера перед вставкой в базу)
    public User(String username, String email, String phone, String password, String first_name, String last_name) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    // Полный конструктор
    public User(int id, String username, String email, String phone, String password, String first_name, String last_name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    // Геттеры и Сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}