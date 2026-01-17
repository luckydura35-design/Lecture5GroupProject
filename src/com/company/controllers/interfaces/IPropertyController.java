package com.company.controllers.interfaces;

public interface IPropertyController {
    String createProperty(int ownerId, int typeId, int addressId, double areaSqm, int roomsCount, int floor, String description);
    String getAllProperties();
    String createPropertyType(String name);
    String createListing(int propertyId, double price, int currencyId);
    String getMyProperties(int ownerId);
    String getAllListings();
}