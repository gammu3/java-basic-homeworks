package ru.otus.java.basic.homeworks.hw19.server;

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
        authenticatedProvider = new PostgresAuthenticatedProvider(this);
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
        if (adminHandler.getUsername().equals(usernameToKick)) {
            adminHandler.sendMsg("Вы не можете отключить себя");
            return;
        }

        for (ClientHandler client : clients) {
            if (client.getUsername().equals(usernameToKick)) {
                try {
                    // Отправляем сообщение о кике
                    client.sendMsg("Вы были отключены администратором");
                    // Даем время на отправку сообщения
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // Удаляем из списка и закрываем соединение
                    clients.remove(client);
//                    client.forceClose();
                    client.disconnect();
                }
                adminHandler.sendMsg("Пользователь " + usernameToKick + " отключен");
                return;
            }
        }
        adminHandler.sendMsg("Пользователь " + usernameToKick + " не найден");
    }
    private void forceDisconnect(ClientHandler client) {
        try {
            // Удаляем из списка клиентов перед закрытием
            clients.remove(client);
            // Закрываем соединение
            client.forceClose();
        } catch (Exception e) {
            System.err.println("Ошибка при принудительном отключении: " + e.getMessage());
        }
    }
//    private void silentDisconnect(ClientHandler client) {
//        try {
//            client.sendMsg("/exitok");
//            clients.remove(client);
//            client.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
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