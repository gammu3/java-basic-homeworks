package ru.otus.java.basic.homeworks.dbmaterials.service;

import ru.otus.java.basic.homeworks.dbmaterials.entity.Role;
import ru.otus.java.basic.homeworks.dbmaterials.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:1234/chatUsersDb";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "admin";

    private static final String USERS_QUERY = "select u.id,u.username,u.password,u.email from users u";

    private static final String USER_ROLES_QUERY = "    select r.id,r.\"name\" from roles r\n" +
            "    join usertorole utr on r.id = utr.role_id\n" +
            "    where utr.user_id = ?;";

    private static final String IS_ADMIN_QUERY = "select count(1) from roles r " +
            "join usertorole utr on r.id = utr.role_id" +
            "    where utr.user_id = ? and r.name = 'admin';";

    private final Connection connection;


    public UserServiceImpl() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(USERS_QUERY)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");

                    User currenUser = new User(username, id, password, email);
                    result.add(currenUser);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement ps = connection.prepareStatement(USER_ROLES_QUERY)) {
            for (User user : result) {
                ps.setInt(1, user.getId());
                List<Role> currentRoles = new ArrayList<>();
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        Role currentRole = new Role(id, name);
                        currentRoles.add(currentRole);
                    }
                }
                user.setRoles(currentRoles);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean isAdmin(int userID) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(IS_ADMIN_QUERY)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag == 1;
    }

    @Override
    public boolean registerUser(String username, String password, String email) {
        String insertUserSql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement ps = connection.prepareStatement(insertUserSql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String assignRoleSql = "INSERT INTO userToRole (user_id, role_id) " +
                        "VALUES (?, (SELECT id FROM roles WHERE name = 'USER'))";
                try (PreparedStatement roleStmt = connection.prepareStatement(assignRoleSql)) {
                    roleStmt.setInt(1, userId);
                    roleStmt.executeUpdate();
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
