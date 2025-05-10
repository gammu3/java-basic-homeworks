package ru.otus.java.basic.homeworks.hw16.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private AuthenticatedProvider authenticatedProvider;

    public Server(int port) {
        this.port = port;
        clients = new CopyOnWriteArrayList<>();
        authenticatedProvider = new InMemoryAuthenticatedProvider(this);
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
        broadcastMessage("Клиент " + clientHandler.getUsername() + " вошел в чат");
        System.out.println("Клиент " + clientHandler.getUsername() + " вошел в чат");
    }

    public void unsubscribe(ClientHandler clientHandler, boolean silent) {
        if (!silent) {
            broadcastMessage("Клиент " + clientHandler.getUsername() + " вышел из чата");
        }
        clients.remove(clientHandler);
    }

    public void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }

    public void kickUser(ClientHandler adminHandler, String usernameToKick) {
        if (!"ADMIN".equals(adminHandler.getRole())) {
            adminHandler.sendMsg("У вас нет прав для этой команды");
            return;
        }

        if (adminHandler.getUsername().equals(usernameToKick)) {
            adminHandler.sendMsg("Вы не можете отключить себя");
            return;
        }

        for (ClientHandler client : clients) {
            if (client.getUsername().equals(usernameToKick)) {
                client.sendMsg("Вы были отключены администратором");
                silentDisconnect(client);
                adminHandler.sendMsg("Пользователь " + usernameToKick + " отключен");
                return;
            }
        }
        adminHandler.sendMsg("Пользователь " + usernameToKick + " не найден");
    }
    private void silentDisconnect(ClientHandler client) {
            clients.remove(client); // Удаляем из списка без broadcast
            client.forceClose();   // Закрываем соединение
    }
    public void sendPrivateMessage(ClientHandler sender, String recipientName, String message) {
        boolean recipientFound = false;

        for (ClientHandler client : clients) {
            if (client.getUsername().equalsIgnoreCase(recipientName)) {
                client.sendMsg("[Личное от " + sender.getUsername() + "]: " + message);
                sender.sendMsg("[Личное для " + recipientName + "]: " + message);
                recipientFound = true;
                break;
            }
        }

        if (!recipientFound) {
            sender.sendMsg("Ошибка: пользователь '" + recipientName + "' не найден или не в сети");
        }
    }
}
