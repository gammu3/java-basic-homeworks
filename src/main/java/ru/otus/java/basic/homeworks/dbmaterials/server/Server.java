package ru.otus.java.basic.homeworks.dbmaterials.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private AuthenticatedProvider authenticatedProvider;
    private Connection connection;

    public Server(int port) {
        this.port = port;
        this.clients = new CopyOnWriteArrayList<>();
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:1234/chatUsersDb",
                    "admin",
                    "admin"
            );
            this.authenticatedProvider = new PostgresAuthenticatedProvider(this);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        System.out.println("Клиент " + clientHandler.getUsername() + " подключился");
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Клиент " + clientHandler.getUsername() + " отключился");
    }

    public boolean isUsernameBusy(String username) {
        return clients.stream().anyMatch(c -> c.getUsername().equals(username));
    }

    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }

    public Connection getConnection() {
        return connection;
    }
}