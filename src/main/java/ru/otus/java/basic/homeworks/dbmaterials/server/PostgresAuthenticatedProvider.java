package ru.otus.java.basic.homeworks.dbmaterials.server;

import ru.otus.java.basic.homeworks.dbmaterials.entity.User;
import ru.otus.java.basic.homeworks.dbmaterials.service.UserService;
import ru.otus.java.basic.homeworks.dbmaterials.service.UserServiceImpl;

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
                String username = rs.getString("username");
                String roleName = rs.getString("role");

                if (server.isUsernameBusy(username)) {
                    clientHandler.sendMsg("Учетная запись уже используется");
                    return false;
                }

                clientHandler.setUsername(username);
                clientHandler.setRole("ADMIN".equalsIgnoreCase(roleName) ? ADMIN : USER);
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
        if (userService.registerUser(username, password, email)) {
            clientHandler.setUsername(username);
            clientHandler.setRole(USER);
            server.subscribe(clientHandler);
            return true;
        }
        return false;
    }
}