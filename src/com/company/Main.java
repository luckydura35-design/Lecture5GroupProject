package com.company;

import com.company.controllers.PropertyController;
import com.company.controllers.UserController;
import com.company.controllers.interfaces.IPropertyController;
import com.company.controllers.interfaces.IUserController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.PropertyRepository;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.IPropertyRepository;
import com.company.repositories.interfaces.IUserRepository;

public class Main {

    public static void main(String[] args) {
        // 1) Инициализируем базу (это база)
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0608", "OOPdb");

        // 2) Инициализация репозитория и контроллера для user класса
        IUserRepository userRepo = new UserRepository(db);
        IUserController userController = new UserController(userRepo);

        // 3) Инициализация репозитория и контроллера для property класса
        IPropertyRepository propertyRepo = new PropertyRepository(db);
        IPropertyController propertyController = new PropertyController(propertyRepo);

        // 4) передаем все в аппликейшн
        MyApplication app = new MyApplication(userController, propertyController);

        // 5) запуск
        app.start();

        // 6) закрытие соединения
        db.close();
    }
}