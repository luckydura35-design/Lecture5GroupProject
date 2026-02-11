package com.company.models;

public class EntityFactory {
    public static User createUser(String username, String email, String phone, String password, String firstName, String lastName) {
        return new User(username, email, phone, password, firstName, lastName);
    }

    public static Property createProperty(int ownerId, int typeId, int addressId, double areaSqm, int roomsCount, int floor, String description, String title) {
        return new Property(ownerId, typeId, addressId, areaSqm, roomsCount, floor, description, title);
    }
}