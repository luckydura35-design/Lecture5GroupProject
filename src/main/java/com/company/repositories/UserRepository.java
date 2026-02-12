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
        // я хз поч это так работает
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

            // NOW() сам заполняет время в дбшке

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public int logIn(String un, String pw) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, un);
            st.setString(2, pw);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // ВОЗВРОЩАЕМ id
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return -1; //если не нашли
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

    @Override
    public boolean updateUserField(int userId, String columnName, String newValue){
        String sql = "UPDATE users SET " + columnName + " = ? WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)){

            st.setString(1, newValue);
            st.setInt(2, userId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        }catch (SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }

    }

    public User findUser(String columnName, String targetValue) {
        String sql = "SELECT * FROM users WHERE " + columnName + "::text = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, targetValue);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getBoolean("is_active"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error in findUser: " + e.getMessage());
        }
        return null;
    }
    public boolean banUser(int targetUserId) {
        String deleteListingsSQL = "DELETE FROM listings WHERE property_id IN (SELECT id FROM properties WHERE owner_id = ?)";
        String deletePropertiesSQL = "DELETE FROM properties WHERE owner_id = ?";
        String deleteUserSQL = "UPDATE users SET is_active = false WHERE id = ?";

        // Используем try-with-resources для автоматического закрытия соединения
        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false); // Начинаем транзакцию

            try {
                // 1. Удаляем листинги
                try (PreparedStatement psListings = con.prepareStatement(deleteListingsSQL)) {
                    psListings.setInt(1, targetUserId);
                    psListings.executeUpdate();
                }

                // 2. Удаляем объекты недвижимости
                try (PreparedStatement psProps = con.prepareStatement(deletePropertiesSQL)) {
                    psProps.setInt(1, targetUserId);
                    psProps.executeUpdate();
                }

                // 3. Баним самого пользователя
                try (PreparedStatement psUser = con.prepareStatement(deleteUserSQL)) {
                    psUser.setInt(1, targetUserId);
                    int rowsUpdated = psUser.executeUpdate();

                    if (rowsUpdated == 0) {
                        con.rollback(); // Если юзера нет, откатываем удаления листингов/проперти
                        return false;
                    }
                }

                // !!! САМЫЙ ВАЖНЫЙ ШАГ !!!
                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback(); // Если случилась ошибка на любом этапе — отменяем всё
                System.out.println("Transaction error, rolling back: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            return false;
        }
    }
}