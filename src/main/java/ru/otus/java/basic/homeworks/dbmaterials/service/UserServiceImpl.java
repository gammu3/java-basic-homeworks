package ru.otus.java.basic.homeworks.dbmaterials.service;

import ru.otus.java.basic.homeworks.dbmaterials.entity.Role;
import ru.otus.java.basic.homeworks.dbmaterials.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService, AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:1234/materialsDB";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "admin";

    private static final String USERS_QUERY = "select u.id,u.username,u.password,u.email from users u";
    private static final String USER_ROLES_QUERY = "select r.id,r.\"name\" from roles r\n" +
            "    join usertorole utr on r.id = utr.role_id\n" +
            "    where utr.user_id = ?;";
    private static final String IS_ADMIN_QUERY = "select count(1) from roles r " +
            "join usertorole utr on r.id = utr.role_id" +
            "    where utr.user_id = ? and r.name = 'admin';";

    private final Connection connection;

    public UserServiceImpl() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            logger.info("Успешное подключение к базе данных");
        } catch (SQLException e) {
            logger.error("Ошибка подключения к базе данных", e);
            throw e;
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Соединение с базой данных закрыто");
            }
        } catch (SQLException e) {
            logger.error("Ошибка при закрытии соединения", e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public List<User> getAll() {
        logger.info("Получение списка всех пользователей");
        List<User> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(USERS_QUERY)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");

                User currentUser = new User(username, id, password, email);
                result.add(currentUser);
            }

            try (PreparedStatement ps = connection.prepareStatement(USER_ROLES_QUERY)) {
                for (User user : result) {
                    ps.setInt(1, user.getId());
                    List<Role> currentRoles = new ArrayList<>();

                    try (ResultSet resultSet = ps.executeQuery()) {
                        while (resultSet.next()) {
                            int roleId = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            Role currentRole = new Role(roleId, name);
                            currentRoles.add(currentRole);
                        }
                    }
                    user.setRoles(currentRoles);
                }
            }

            logger.info("Успешно получено {} пользователей", result.size());
        } catch (SQLException e) {
            logger.error("Ошибка при получении списка пользователей", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean isAdmin(int userID) {
        if (userID <= 0) {
            logger.warn("Попытка проверки прав администратора для недопустимого ID: {}", userID);
            return false;
        }

        logger.info("Проверка прав администратора для пользователя с ID: {}", userID);
        int flag = 0;

        try (PreparedStatement ps = connection.prepareStatement(IS_ADMIN_QUERY)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flag = rs.getInt(1);
                }
            }

            boolean isAdmin = flag == 1;
            logger.info("Пользователь с ID {} {} администратором", userID, isAdmin ? "является" : "не является");
            return isAdmin;
        } catch (SQLException e) {
            logger.error("Ошибка при проверке прав администратора", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean registerUser(String username, String password, String email) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            logger.warn("Попытка регистрации пользователя с невалидными данными");
            return false;
        }

        logger.info("Регистрация нового пользователя: {}", username);
        String insertUserSql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?) RETURNING id";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(insertUserSql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        String assignRoleSql = "INSERT INTO userToRole (user_id, role_id) " +
                                "VALUES (?, (SELECT id FROM roles WHERE name = 'USER'))";

                        try (PreparedStatement roleStmt = connection.prepareStatement(assignRoleSql)) {
                            roleStmt.setInt(1, userId);
                            roleStmt.executeUpdate();
                        }

                        connection.commit();
                        logger.info("Пользователь {} успешно зарегистрирован с ID: {}", username, userId);
                        return true;
                    }
                }

                connection.rollback();
                logger.warn("Не удалось зарегистрировать пользователя: {}", username);
                return false;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Ошибка при откате транзакции", ex);
            }
            logger.error("Ошибка при регистрации пользователя", e);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Ошибка при восстановлении autoCommit", e);
            }
        }
    }
}