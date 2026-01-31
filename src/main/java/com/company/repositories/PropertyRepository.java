package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Property;
import com.company.repositories.interfaces.IPropertyRepository;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class PropertyRepository implements IPropertyRepository {
    private final IDB db;

    public PropertyRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createProperty(Property property) {
        // Тут Ску Л запрос, вопросики снизу заполняються через гетеры
        String sql = "INSERT INTO properties(owner_id, type_id, address_id, area_sqm, rooms_count, floor, description, title ,created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, property.getOwnerId());
            st.setInt(2, property.getTypeId());
            st.setInt(3, property.getAddressId());
            st.setDouble(4, property.getAreaSqm());
            st.setInt(5, property.getRoomsCount());
            st.setInt(6, property.getFloor());
            st.setString(7, property.getDescription());
            st.setString(8, property.getTitle());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error in createProperty: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Property> getAllProperties() {
        String sql = "SELECT id, owner_id, type_id, address_id, area_sqm, rooms_count, floor, description, title FROM properties";
        List<Property> list = new ArrayList<>();
        try (Connection con = db.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Property p = new Property(
                        rs.getInt("owner_id"), rs.getInt("type_id"),
                        rs.getInt("address_id"), rs.getDouble("area_sqm"),
                        rs.getInt("rooms_count"), rs.getInt("floor"), rs.getString("description"),
                        rs.getString("title")
                );
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean createPropertyType(String name) {
        String sql = "INSERT INTO property_types(name) VALUES (?)";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, name);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createListing(int propertyId, double price, int currencyId) {
        String sql = "INSERT INTO listings(property_id, price, currency_id, status, listed_at) " +
                "VALUES (?, ?, ?, 'active', NOW())";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, propertyId);
            st.setDouble(2, price);
            st.setInt(3, currencyId);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Property> getMyProperties(int ownerId) {
        String sql = "SELECT * FROM properties WHERE owner_id = ?";
        List<Property> list = new ArrayList<>();
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, ownerId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Property(
                        rs.getInt("id"),
                        rs.getInt("owner_id"), rs.getInt("type_id"),
                        rs.getInt("address_id"), rs.getDouble("area_sqm"),
                        rs.getInt("rooms_count"), rs.getInt("floor"), rs.getString("description"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) { System.out.println("SQL error: " + e.getMessage()); }
        return list;
    }

    @Override
    public String getAllListings() {
        // Добавляем JOIN users и выбираем u.username
        String sql = "SELECT l.id, p.title ,p.description, l.price, c.code, p.area_sqm, u.username " +
                "FROM listings l " +
                "JOIN properties p ON l.property_id = p.id " +
                "JOIN currencies c ON l.currency_id = c.id " +
                "JOIN users u ON p.owner_id = u.id " + // Присоединяем таблицу пользователей
                "WHERE l.status = 'active'";

        StringBuilder sb = new StringBuilder();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                // Добавляем %s для юзернейма в формат строки
                sb.append(String.format("ID: %d | Seller: %s | title: %s | %s | Price: %.2f %s | Area: %.1f sqm\n",
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("title"),// Достаем никнейм
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("code"),
                        rs.getDouble("area_sqm")));
            }
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
        return sb.toString().isEmpty() ? "Market is empty." : sb.toString();
    }
}