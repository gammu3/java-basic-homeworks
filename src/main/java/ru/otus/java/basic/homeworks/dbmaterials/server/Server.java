package ru.otus.java.basic.homeworks.dbmaterials.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final String DB_URL = "jdbc:postgresql://localhost:1234/chatUsersDb";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";

    private final int port;
    private final List<ClientHandler> clients;
    private final AuthenticatedProvider authenticatedProvider;
    private final Connection connection;

    public Server(int port) {
        this.port = port;
        this.clients = new CopyOnWriteArrayList<>();
        try {
            logger.info("Инициализация подключения к базе данных");
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            this.authenticatedProvider = new PostgresAuthenticatedProvider(this);
            logger.info("Сервер успешно инициализирован");
        } catch (SQLException e) {
            logger.error("Ошибка подключения к базе данных", e);
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }

    public void start() {
        logger.info("Запуск сервера на порту {}", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен и ожидает подключений");
            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("Получено новое подключение");
                try {
                    new ClientHandler(socket, this);
                    logger.info("Обработчик клиента создан");
                } catch (IOException e) {
                    logger.error("Ошибка при создании обработчика клиента", e);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка в работе сервера", e);
            throw new RuntimeException(e);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        logger.info("Клиент {} подключился. Всего клиентов: {}",
                clientHandler.getUsername(), clients.size());
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        logger.info("Клиент {} отключился. Осталось клиентов: {}",
                clientHandler.getUsername(), clients.size());
    }

    public boolean isUsernameBusy(String username) {
        boolean busy = clients.stream().anyMatch(c -> c.getUsername().equals(username));
        if (busy) {
            logger.debug("Имя пользователя {} уже используется", username);
        }
        return busy;
    }

    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }

    public Connection getConnection() {
        return connection;
    }
}