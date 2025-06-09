package ru.otus.java.basic.homeworks.hw19;

import ru.otus.java.basic.homeworks.hw19.entity.User;
import ru.otus.java.basic.homeworks.hw19.service.UserService;
import ru.otus.java.basic.homeworks.hw19.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        List<User> userList = userService.getAll();
        System.out.println("userList = " + userList);
        if (!userList.isEmpty()){
            System.out.println("user : " + userList.get(0) + " является ли администратором? "
                    + userService.isAdmin(userList.get(0).getId()));
        }
    }
}
