package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.User;
import com.company.repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createUser(User user) {
        // Добавляем created_at и is_active в список колонок
        String sql = "INSERT INTO users(username, email, phone, password, first_name, last_name, created_at, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW(), true)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, user.getUsername());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhone());
            st.setString(4, user.getPassword());
            st.setString(5, user.getFirstName());
            st.setString(6, user.getLastName());

            // Мы заполнили 6 знаков вопроса. NOW() и true база подставит сама.

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public int logIn(String un, String pw) {
        // ВАЖНО: Судя по твоему прошлому коду, поле в базе может называться password или password_hash.
        // Я использую "password", как в твоем скриншоте ER-диаграммы.
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, un);
            st.setString(2, pw);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // Возвращаем реальный ID из базы
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return -1; // Если юзер не найден или произошла ошибка
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT id, username, email, phone, password, first_name, last_name FROM users";
        List<User> users = new ArrayList<>();

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }
}