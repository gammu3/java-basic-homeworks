package ru.otus.java.basic.homeworks.hw19.server;

import ru.otus.java.basic.homeworks.hw19.entity.User;
import ru.otus.java.basic.homeworks.hw19.service.UserService;
import ru.otus.java.basic.homeworks.hw19.service.UserServiceImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresAuthenticatedProvider implements AuthenticatedProvider {
    private final Server server;
    private final UserService userService;
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    public PostgresAuthenticatedProvider(Server server) {
        this.server = server;
        try {
            this.userService = new UserServiceImpl();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка инициализации UserService", e);
        }
    }

//    @Override
//    public void initialize() {
//        System.out.println("Аутентификация PostgreSQL инициализирована.");
//    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String email, String password) {
        String findUserSql = "SELECT u.id, u.username, r.name as role " +
                "FROM users u " +
                "LEFT JOIN userToRole utr ON u.id = utr.user_id " +
                "LEFT JOIN roles r ON utr.role_id = r.id " +
                "WHERE u.email = ? AND u.password = ?";

        try (PreparedStatement stmt = userService.getConnection().prepareStatement(findUserSql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String username = rs.getString("username");
                String roleName = rs.getString("role");

                if (server.isUsernameBusy(username)) {
                    clientHandler.sendMsg("Учетная запись уже используется");
                    return false;
                }

                clientHandler.setUsername(username);

                if ("ADMIN".equalsIgnoreCase(roleName)) {
                    clientHandler.setRole(ADMIN);
                } else {
                    clientHandler.setRole(USER);
                }

                server.subscribe(clientHandler);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        clientHandler.sendMsg("Неверный email или пароль");
        return false;
    }

    @Override
    public boolean registration(ClientHandler clientHandler, String email, String password, String username) {
        if (email.length() < 5) {
            clientHandler.sendMsg("Ошибка: email должен содержать не менее 5 символов");
            return false;
        }
        if (password.length() < 3) {
            clientHandler.sendMsg("Ошибка: пароль должен содержать не менее 3 символов");
            return false;
        }
        if (username.length() < 3) {
            clientHandler.sendMsg("Ошибка: имя пользователя должно содержать не менее 3 символов");
            return false;
        }

        for (User user : userService.getAll()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                clientHandler.sendMsg("Email уже занят");
                return false;
            }
            if (user.getUsername().equalsIgnoreCase(username)) {
                clientHandler.sendMsg("Имя пользователя уже занято");
                return false;
            }
        }

        if (userService.registerUser(username, password, email)) {
            clientHandler.setUsername(username);
            clientHandler.setRole(USER);
            server.subscribe(clientHandler);
            clientHandler.sendMsg("/regok " + username);
            return true;
        } else {
            clientHandler.sendMsg("Ошибка при регистрации");
            return false;
        }
    }
}