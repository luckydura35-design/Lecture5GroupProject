package com.company.models;

public class User {
    private int id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String first_name;
    private String last_name;
    private boolean active;

    public User() {
    }

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

    public User(int id, String username, String email, String phone, String password, String first_name, String last_name, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.active = active;
    }


    // геттеры и Сеттеры
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return first_name;
    }
    public String getLastName() {
        return last_name;
    }
    public boolean is_active() {return active;}



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