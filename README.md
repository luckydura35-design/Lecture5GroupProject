# Real Estate Database

## Project Description

This repository contains an SQL database schema and seed data for a real estate management system. The database is designed to store structured information about locations, users, properties, and real estate listings.

The project can be used as:

* an educational example of relational database design;
* a backend foundation for a web or mobile application;
* a practice database for SQL queries, joins, and constraints.

---

## Database Structure

### 1. Countries

Stores a list of countries.

**Fields:**

* `id` — primary key
* `name` — country name

---

### 2. Cities

Represents cities within a country (one-to-many relationship).

**Fields:**

* `id` — primary key
* `country_id` — foreign key → `countries(id)`
* `name` — city name

---

### 3. Streets

Stores streets belonging to a specific city.

**Fields:**

* `id` — primary key
* `city_id` — foreign key → `cities(id)`
* `name` — street name

---

### 4. Addresses

Represents exact physical addresses.

**Fields:**

* `id` — primary key
* `street_id` — foreign key → `streets(id)`
* `house_number` — house number
* `apartment_number` — apartment number
* `postal_code` — postal code

---

### 5. Property Types

Reference table defining types of real estate.

**Fields:**

* `id` — primary key
* `name` — property type (Apartment, House, Office, etc.)

---

### 6. Users

Stores system users (property owners).

**Fields:**

* `id` — primary key
* `username` — unique username
* `email` — unique email address
* `phone` — phone number
* `password` — encrypted password
* `first_name` — first name
* `last_name` — last name
* `created_at` — registration timestamp
* `is_active` — account status

---

### 7. Properties

Main table describing real estate objects.

**Fields:**

* `id` — primary key
* `owner_id` — foreign key → `users(id)`
* `type_id` — foreign key → `property_types(id)`
* `address_id` — foreign key → `addresses(id)`
* `area_sqm` — area in square meters
* `rooms_count` — number of rooms
* `floor` — floor number
* `description` — additional description
* `created_at` — creation timestamp

---

### 8. Currencies

Reference table for supported currencies.

**Fields:**

* `id` — primary key
* `code` — currency code (KZT, USD, EUR)
* `symbol` — currency symbol

---

### 9. Listings

Represents real estate listings with pricing information.

**Fields:**

* `id` — primary key
* `property_id` — foreign key → `properties(id)`
* `price` — listing price
* `currency_id` — foreign key → `currencies(id)`
* `status` — listing status
* `listed_at` — publication timestamp
* `updated_at` — last update timestamp

---

## Table Relationships

* Countries → Cities → Streets → Addresses
* Users → Properties → Listings
* Property Types → Properties
* Currencies → Listings

Foreign keys are configured with `ON DELETE CASCADE` where logically required.

---

## Seed Data

The database includes example seed data for testing purposes:

* countries (Kazakhstan, USA, United Kingdom)
* cities and streets in Kazakhstan
* sample addresses
* property types
* currencies

---


## Authors
Adilkhan Kaiyrbek,Nursaya Klusheva,Azhar Aidarbek.
