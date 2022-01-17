package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        List<User> listUsers = new ArrayList<>();
        listUsers.add(new User("Владимир", "Пупкин", (byte) 77));
        listUsers.add(new User("Вовка", "Соловьев", (byte) 54));
        listUsers.add(new User("Джонни", "Кольт", (byte) 45));
        listUsers.add(new User("Джейсон", "Стейтем", (byte) 50));
        listUsers.forEach(x-> {
            userService.saveUser(x.getName(), x.getLastName(), x.getAge());
            System.out.format("User с именем – %s добавлен в базу данных%n", x.getName());
        });
        userService.getAllUsers().forEach(user -> System.out.println(user.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}
