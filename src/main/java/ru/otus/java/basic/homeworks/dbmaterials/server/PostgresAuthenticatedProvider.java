package ru.otus.java.basic.homeworks.dbmaterials.server;

import ru.otus.java.basic.homeworks.dbmaterials.entity.User;
import ru.otus.java.basic.homeworks.dbmaterials.service.UserService;
import ru.otus.java.basic.homeworks.dbmaterials.service.UserServiceImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresAuthenticatedProvider implements AuthenticatedProvider {
    private static final Logger logger = LoggerFactory.getLogger(PostgresAuthenticatedProvider.class);
    private final Server server;
    private final UserService userService;

    public PostgresAuthenticatedProvider(Server server) {
        this.server = server;
        try {
            this.userService = new UserServiceImpl();
            logger.info("PostgresAuthenticatedProvider инициализирован");
        } catch (SQLException e) {
            logger.error("Ошибка инициализации UserService", e);
            throw new RuntimeException("Ошибка инициализации UserService", e);
        }
    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String email, String password) {
        logger.info("Попытка аутентификации пользователя с email: {}", email);
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
                    logger.warn("Учетная запись {} уже используется", username);
                    clientHandler.sendMsg("Учетная запись уже используется");
                    return false;
                }

                UserRole role = "ADMIN".equalsIgnoreCase(roleName) ? UserRole.ADMIN : UserRole.USER;
                clientHandler.setUsername(username);
                clientHandler.setRole(UserRole.ADMIN);
                server.subscribe(clientHandler);
                logger.info("Успешная аутентификация пользователя {} с ролью {}", username, role);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Ошибка аутентификации пользователя", e);
            throw new RuntimeException(e);
        }

        logger.warn("Неудачная попытка аутентификации для email: {}", email);
        clientHandler.sendMsg("Неверный email или пароль");
        return false;
    }

    @Override
    public boolean registration(ClientHandler clientHandler, String email, String password, String username) {
        logger.info("Попытка регистрации нового пользователя: {}", username);
        if (userService.registerUser(username, password, email)) {
            clientHandler.setUsername(username);
            clientHandler.setRole(UserRole.USER);
            server.subscribe(clientHandler);
            logger.info("Успешная регистрация пользователя {}", username);
            return true;
        }
        logger.warn("Неудачная попытка регистрации пользователя {}", username);
        return false;
    }
}