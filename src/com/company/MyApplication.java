package com.company;

import com.company.controllers.interfaces.IUserController;
import com.company.controllers.interfaces.IPropertyController;
import java.util.regex.Pattern;
import com.company.models.User; // Не забудь импорт, если будешь его использовать

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IUserController userController;
    private final IPropertyController propertyController;
    private int currentUserId = -1;
    private boolean is_user_logged_in = false; // статик не ставте (не работает)

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
        System.out.println("5. [Admin] Show all users");
        System.out.println("6. Log Out");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
    }

    public void start() {
        while (true) {
            if (!is_user_logged_in) {
                authMenu();
                try {
                    int option = scanner.nextInt();
                    if (option == 1) register();
                    else if (option == 2) logIn();
                    else if (option == 0) break;
                    else System.out.println("Invalid option!");
                } catch (InputMismatchException e) {
                    System.out.println("Input must be integer!");
                    scanner.nextLine();
                }
            } else {
                mainMenu();
                try {
                    int option = scanner.nextInt();
                    switch (option) {
                        case 1: showMarket(); break;
                        case 2: postListing(); break;
                        case 3: addProperty(); break;
                        case 4: showMyProperties(); break;
                        case 5: showAllUsers(); break;
                        case 6: is_user_logged_in = false; currentUserId = -1; break;
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

        int response = userController.logIn(username, password);

        if (response > -1) {
            System.out.println("You logged in! 👌");
            is_user_logged_in = true;
            currentUserId = response;
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

            // Вызываем контроллер
            String response = propertyController.createProperty(
                    currentUserId, typeId, addressId, area, rooms, floor, description
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
}