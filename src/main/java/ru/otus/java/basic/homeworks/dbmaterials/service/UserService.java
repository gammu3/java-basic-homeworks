package ru.otus.java.basic.homeworks.dbmaterials.service;

import ru.otus.java.basic.homeworks.dbmaterials.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface UserService {
    List<User> getAll();
    boolean isAdmin(int userID);
    boolean registerUser(String username, String password, String email);
    Connection getConnection() throws SQLException;
}
