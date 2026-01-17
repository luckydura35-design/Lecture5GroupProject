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
        // 1. Инициализация базы данных
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0608", "OOPdb");

        // 2. Инициализация слоев для Пользователей (User)
        IUserRepository userRepo = new UserRepository(db);
        IUserController userController = new UserController(userRepo);

        // 3. Инициализация слоев для Недвижимости (Property)
        IPropertyRepository propertyRepo = new PropertyRepository(db);
        IPropertyController propertyController = new PropertyController(propertyRepo);

        // 4. Передаем оба контроллера в приложение2
        // (Убедись, что ты обновил конструктор в классе MyApplication, как мы обсуждали ранее)
        MyApplication app = new MyApplication(userController, propertyController);

        // 5. Запуск
        app.start();

        // 6. Закрытие соединения
        db.close();
    }
}