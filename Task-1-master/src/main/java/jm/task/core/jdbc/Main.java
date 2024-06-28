package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // Создание таблицы User
        userService.createUsersTable();

        // Добавление пользователей в таблицу
        userService.saveUser("Владимир", "Сазанов", (byte) 30);
        userService.saveUser("Нурбек", "Тулендинов", (byte) 25);
        userService.saveUser("Егор", "Крид", (byte) 35);
        userService.saveUser("Товаришь", "Товарищев", (byte) 28);

        // Получение всех пользователей и вывод в консоль
        List<User> users = userService.getAllUsers();
        System.out.println("All users:");
        users.forEach(System.out::println);

        // Очистка таблицы User
        userService.cleanUsersTable();

        // Удаление таблицы User
        //userService.dropUsersTable();
    }
}
