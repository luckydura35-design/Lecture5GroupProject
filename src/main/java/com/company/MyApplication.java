package com.company;

import com.company.controllers.interfaces.IUserController;
import com.company.controllers.interfaces.IPropertyController;
import com.company.models.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IUserController userController;
    private final IPropertyController propertyController;
    private int currentUserId = -1;
    private boolean is_user_logged_in = false; // статик не ставте (не работает)
    private boolean is_user_admin = false;

    public MyApplication(IUserController userController, IPropertyController propertyController) {
        this.userController = userController;
        this.propertyController = propertyController;
    }

    private void authMenu() {
        System.out.println("\n--- Welcome to My Application ---");
        System.out.println("1. Register");
        System.out.println("2. Log In");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
    }

    private void mainMenu() {
        System.out.println("\n--- Real Estate Market (Your ID: " + currentUserId + ") ---");
        System.out.println("1. [Market] Show all listings for sale");
        System.out.println("2. [Market] Post my property for sale (Add an option)");
        System.out.println("3. [My] Add a new property to my collection");
        System.out.println("4. [My] Show my properties");
        System.out.println("5. Log Out");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
    }

    private void adminMenu() {
        System.out.println("\n--- ADMIN MENU (You`re AN ADMIN!)");
        System.out.println("1. [Admin] Show all users");
        System.out.println("2. [Admin] Change user data");
        System.out.println("3. [Admin] ban user");
        System.out.println("4. [Admin] find user");
        System.out.println("5. Log Out");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
    }

    public void start() {
        while (true) {
            if (!is_user_logged_in) {
                authMenu();
                try {
                    int option = scanner.nextInt();
                    switch (option) {
                        case 1: register(); break;
                        case 2: logIn(); break;
                        case 0: break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input must be integer!");
                    scanner.nextLine();
                }
            } else {
                if(is_user_admin) {
                    adminMenu();
                    try {
                        int option = scanner.nextInt();
                        switch (option) {
                            case 1: showAllUsers(); break;
                            case 2: updateUserData(); break;
                            case 3: banUser(); break;
                            case 4: findUser(); break;
                            case 0: return;
                            default: System.out.println("Invalid option!"); break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Input must be integer!");
                        scanner.nextLine();
                    }
                }
                else {
                    mainMenu();
                    try {
                        int option = scanner.nextInt();
                        switch (option) {
                            case 1: showMarket(); break;
                            case 2: postListing(); break;
                            case 3: addProperty(); break;
                            case 4: showMyProperties(); break;
                            case 5: is_user_logged_in = false; currentUserId = -1; break;
                            case 0: return;
                            default: System.out.println("Invalid option!"); break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Input must be integer!");
                        scanner.nextLine();
                    }
                }
            }
        }
    }

    public void register() {
        System.out.println("--- Registration Form ---");
        String username;
        System.out.print("Username: ");
        username = scanner.next();

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.next();
            if (email.contains("@") && email.contains(".")) break;
            System.out.println("Error: Email must contain @ and dot.");
        }


        String phone;
        while (true) {
            System.out.print("Phone: ");
            phone = scanner.next();
            boolean isAllDigits = phone.replace("+", "").chars().allMatch(Character::isDigit);
            int len = phone.length();

            if (isAllDigits && len >= 10 && len <= 13) break;

            System.out.println("Error: Invalid phone (10-12 digits required).");
        }
        String password;
        while (true) {
            System.out.print("Password: ");
            password = scanner.next();
            if (password.length() >= 6) break;
            System.out.println("Error: Password must be at least 6 characters long.");
        }
        System.out.print("First Name: ");
        String first_name = scanner.next();
        System.out.print("Last Name: ");
        String last_name = scanner.next();

        try {
            boolean response = userController.createUser(username, email, phone, password, first_name, last_name);

            if (response) {
                System.out.println("✅ Account has been created! Now you can log in.");
            } else {
                System.out.println("❌ Something went wrong during registration (database error).");
            }
        } catch (Exception e) {
            // Ловим возможный NullPointerException, если база не подключена
            System.out.println("❌ Fatal Error: Database connection is not established.");
        }
    }

    public void logIn() {
        System.out.println("\n--- Log In ---");
        System.out.print("Username: "); String username = scanner.next();
        System.out.print("Password: "); String password = scanner.next();

        int user_id = userController.logIn(username, password);

        if (user_id > -1) {
            System.out.println("You logged in! 👌");
            is_user_logged_in = true;
            currentUserId = user_id;
            if (currentUserId == 777) {is_user_admin = true;}
        } else {
            System.out.println("Invalid username or password! 😥");
        }
    }
    private void addProperty() {
        System.out.println("\n--- Add New Property ---");

        try {

            System.out.print("Enter Property Type ID \n" +
                    "(1\t\"Kazakhstan\"\n" +
                    "2\t\"USA\"\n" +
                    "3\t\"United Kingdom\"): ");
            int typeId = scanner.nextInt();

            System.out.print("Enter Address ID: ");
            int addressId = scanner.nextInt();

            System.out.print("Enter Area (sqm): ");
            double area = scanner.nextDouble();

            System.out.print("Enter Rooms Count: ");
            int rooms = scanner.nextInt();

            System.out.print("Enter Floor: ");
            int floor = scanner.nextInt();

            scanner.nextLine(); // Очистка буфера сканера после nextInt
            System.out.print("Enter Description: ");
            String description = scanner.nextLine();

            scanner.nextLine();
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();

            // Вызываем контроллер
            String response = propertyController.createProperty(
                    currentUserId, typeId, addressId, area, rooms, floor, description, title
            );

            System.out.println(response);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numbers where required.");
            scanner.nextLine(); // Очистка буфера
        }
    }
    private void showAllProperties() {
        System.out.println("\n--- All Properties ---");
        System.out.println(propertyController.getAllProperties());
    }
    private void showAllUsers() {
        System.out.println("\n--- All Users ---");
        System.out.println(userController.getAllUsers());
    }
    private void showMarket() {
        System.out.println("\n--- Current Market Listings ---");
        System.out.println(propertyController.getAllListings());
    }
    private void postListing() {
        System.out.println("\n--- Post Property for Sale ---");
        // Сначала показываем юзеру, что у него есть
        System.out.println("Your properties:");
        System.out.println(propertyController.getMyProperties(currentUserId));

        System.out.print("Enter Property ID you want to sell: ");
        int propId = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Currency ID (1: KZT, 2: USD, 3: EUR): ");
        int currId = scanner.nextInt();

        System.out.println(propertyController.createListing(propId, price, currId));
    }
    private void showMyProperties() {
        System.out.println("\n--- Your Properties ---");
        System.out.println(propertyController.getMyProperties(currentUserId));
    }

    private void updateUserData() {
        System.out.print("Enter User ID to update: ");
        int targetUserId = scanner.nextInt();

        // TODO: Сделать проверку по айди, есть ли такой пользователь вообще

        System.out.println("\n--- What do you want to change? ---");
        System.out.println("1. Username");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.println("4. Password");
        System.out.println("0. Back");
        System.out.print("Select: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера после nextInt()

        String columnName = "";
        String newValue = "";

        switch (choice) {
            case 1: columnName = "username";break;
            case 2: columnName = "email"; break;
            case 3: columnName = "phone"; break;
            case 4: columnName = "password"; break;
            case 0: return;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.print("Enter new value for " + columnName + ": ");
        newValue = scanner.nextLine();

        // отправляем в контроллер
        boolean success = userController.updateUserField(targetUserId, columnName, newValue);

        if (success) {
            System.out.println("✅ User updated successfully!");
        } else {
            System.out.println("❌ Failed to update user.");
        }
    }

    private boolean banUser(){
        System.out.print("Enter User ID to update: ");
        int targetUserId = scanner.nextInt();

        return userController.banUser(targetUserId);
    }
    private void findUser(){
        System.out.println("\n--- Find user menu ---");
        System.out.println("1. Find user by Username");
        System.out.println("2. Find user by Email");
        System.out.println("3. Find user by Phone");
        System.out.println("4. Find user by Password");
        System.out.println("0. Back");
        System.out.print("Select: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String columnName = "";
        String targetValue = "";

        switch (choice) {
            case 1: columnName = "username"; break;
            case 2: columnName = "email"; break;
            case 3: columnName = "phone"; break;
            case 4: columnName = "password"; break;
        }

        System.out.print("Enter target value for " + columnName + ": ");
        targetValue = scanner.nextLine();

        User user = userController.findUser(columnName, targetValue);

        System.out.println(user);

    }
}