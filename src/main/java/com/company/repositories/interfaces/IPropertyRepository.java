package com.company.repositories.interfaces;

import com.company.models.Property;
import java.util.List;

public interface IPropertyRepository {
    boolean createProperty(Property property);
    List<Property> getAllProperties();
    boolean createPropertyType(String name);
    boolean createListing(int propertyId, double price, int currencyId);
    List<Property> getMyProperties(int ownerId);
    String getAllListings();
    // Впадлу добавлять ещеу
}