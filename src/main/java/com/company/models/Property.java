package com.company.models;

public class Property {
    private int id;
    private int ownerId;
    private int typeId;
    private int addressId;
    private double areaSqm;
    private int roomsCount;
    private int floor;
    private String description;
    private String title;

    public Property(int ownerId, int typeId, int addressId, double areaSqm, int roomsCount, int floor, String description, String title) {
        this.ownerId = ownerId;
        this.typeId = typeId;
        this.addressId = addressId;
        this.areaSqm = areaSqm;
        this.roomsCount = roomsCount;
        this.floor = floor;
        this.description = description;
        this.title = title;
    }

    public Property(int id,int ownerId, int typeId, int addressId, double areaSqm, int roomsCount, int floor, String description, String title) {
        this.id = id;
        this.ownerId = ownerId;
        this.typeId = typeId;
        this.addressId = addressId;
        this.areaSqm = areaSqm;
        this.roomsCount = roomsCount;
        this.floor = floor;
        this.description = description;
        this.title = title;
    }

    // Геттеры
    public int getOwnerId() { return ownerId; }
    public int getTypeId() { return typeId; }
    public int getAddressId() { return addressId; }
    public double getAreaSqm() { return areaSqm; }
    public int getRoomsCount() { return roomsCount; }
    public int getFloor() { return floor; }
    public String getDescription() { return description; }
    public String getTitle() { return title; }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", addressId=" + addressId +
                ", area=" + areaSqm + " sqm" +
                ", rooms=" + roomsCount +
                ", floor=" + floor +
                ", desc='" + description +
                ", title='" + title + '\'' +
                '}';
    }
}