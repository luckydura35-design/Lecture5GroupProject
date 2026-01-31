# Real Estate Database & Application

## Project Description

This project is a real estate management system consisting of:

* a relational **PostgreSQL database** (First Iteration),
* a **Java console application** using JDBC (Second Iteration).

The system stores structured information about users, properties, addresses, and listings, and demonstrates practical application of SQL, object-oriented programming, and software design principles.

The project can be used as:

* an educational example of relational database design;
* a backend foundation for a real estate application;
* a practice project for SQL JOINs, Java JDBC, SOLID, and design patterns.

---

# First Iteration – Database Design

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
* `name` — property type

---

### 6. Users

Stores system users (property owners).

**Fields:**

* `id` — primary key
* `username` — unique username
* `email` — unique email
* `phone` — phone number
* `password` — password
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
* `description` — description
* `created_at` — creation timestamp

---

### 8. Currencies

Reference table for supported currencies.

**Fields:**

* `id` — primary key
* `code` — currency code
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

Foreign keys are configured with cascading rules where appropriate.

---

## Seed Data

The database includes example data:

* countries (Kazakhstan, USA, United Kingdom),
* cities, streets, and addresses,
* property types,
* currencies.

---

# Second Iteration – Java Application & Architecture

The second iteration extends the database into a **fully functional Java console application** using JDBC.
This iteration focuses on application logic, clean architecture, and modern Java features.

---

## Application Architecture

The application follows a layered architecture:

* **Models** — domain entities (`User`, `Property`);
* **Repositories** — database access, SQL queries, JOINs, transactions;
* **Controllers** — business logic and validation;
* **Application Layer** — console UI (`MyApplication`).

All layers communicate through interfaces, ensuring loose coupling.

---

## Implementation of JOINs

SQL `JOIN` operations are actively used to combine data from multiple tables.

### Example: Market Listings Aggregation

The method `getAllListings()` retrieves full listing information using JOINs between:

* `listings`,
* `properties`,
* `currencies`,
* `users`.

This allows the application to display a **complete listing description** (seller, price, currency, area, description) using a single query, demonstrating efficient relational data aggregation.

---

## Application of Lambda Expressions (Streams API)

Manual loops were replaced with Java Stream API and lambda expressions.

### Refactored Methods

* `UserController.getAllUsers()`
* `PropertyController.getAllProperties()`
* `PropertyController.getMyProperties()`

Collections are processed using:

* `stream()`
* `map()`
* `Collectors.joining("\n")`

This improves readability, reduces boilerplate code, and clearly expresses data transformation intent.

### Input Validation

Lambda expressions are also used in input validation (e.g., phone number validation using character streams and predicates).

---

## Design Patterns

### Singleton

The `PostgresDB` class implements the **Singleton pattern** to ensure:

* a single database connection manager,
* centralized configuration,
* consistent database access.

### Dependency Injection

Controllers depend on repository interfaces, and repositories depend on a database abstraction (`IDB`).
This improves flexibility, testability, and separation of concerns.

---

## SOLID Principles

The project follows SOLID principles:

* **Single Responsibility Principle** — each class has one responsibility;
* **Open/Closed Principle** — functionality is extended without modifying core logic;
* **Liskov Substitution Principle** — interfaces allow interchangeable implementations;
* **Interface Segregation Principle** — interfaces are minimal and focused;
* **Dependency Inversion Principle** — high-level modules depend on abstractions.

---

## Role Management and Secured Functionality

Basic role-based access control is implemented:

### Regular Users

* Register and log in,
* Add and view properties,
* Create listings,
* View market listings.

### Admin Users

* View all users,
* Update user data,
* Search users,
* Ban users with transactional safety.

Administrative features are isolated via a dedicated admin menu.

---

## Second Iteration Summary

The second iteration adds:

* Java JDBC application logic,
* real SQL JOIN usage,
* design patterns (Singleton, DI),
* lambda expressions and Streams,
* SOLID-compliant architecture,
* role-based access control.

---

## Authors

* Adilkhan Kaiyrbek
* Nursaya Klusheva
* Azhar Aidarbek
