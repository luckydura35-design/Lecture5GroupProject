package com.company.controllers;

import com.company.controllers.interfaces.IPropertyController;
import com.company.models.Property;
import com.company.repositories.interfaces.IPropertyRepository;
import java.util.List;
import java.util.stream.Collectors;


public class PropertyController implements IPropertyController {
    private final IPropertyRepository repo;

    public PropertyController(IPropertyRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createProperty(int ownerId, int typeId, int addressId, double areaSqm, int roomsCount, int floor, String description) {
        Property property = new Property(ownerId, typeId, addressId, areaSqm, roomsCount, floor, description);

        boolean created = repo.createProperty(property);

        return (created) ? "Property was created!" : "Property creation failed!";
    }
    @Override
    public String getAllProperties() {
        List<Property> properties = repo.getAllProperties();
        if (properties == null) return "Error happened!";

        String result = properties.stream()
                .map(p ->p.toString())
                .collect(Collectors.joining("\n"));

        return result.isEmpty() ? "No properties found." : result;
    }

    @Override
    public String createPropertyType(String name) {
        boolean created = repo.createPropertyType(name);
        return created ? "Property type added!" : "Failed to add type!";
    }
    @Override
    public String createListing(int propertyId, double price, int currencyId) {
        return repo.createListing(propertyId, price, currencyId) ? "Listing posted!" : "Failed to post listing!";
    }

    @Override
    public String getMyProperties(int ownerId) {
        List<Property> props = repo.getMyProperties(ownerId);
        if (props.isEmpty()) return "You don't own any properties.";

        return  props.stream()
                .map(p -> p.toString())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getAllListings() {
        return repo.getAllListings();
    }
}