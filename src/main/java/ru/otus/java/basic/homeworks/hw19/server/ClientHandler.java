package ru.otus.java.basic.homeworks.hw19.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String role;
    private boolean authenticated;
    private String username;


    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
//                System.out.println("Клиент подключился " + socket.getPort());
                while (true) {
                    sendMsg("Перед работой с чатом необходимо выполнить " +
                            "аутентификацию '/log email password' \n" +
                            "или регистрацию '/reg email password username'");
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMsg("/exitok");
                            break;
                        }
                        if (message.startsWith("/log ")) {
                            String token[] = message.split(" ");
                            if (token.length != 3) {
                                sendMsg("Неверный формат команды /log");
                                continue;
                            }
                            if (server.getAuthenticatedProvider()
                                    .authenticate(this, token[1], token[2])) {
                                authenticated = true;
                                break;
                            }
                        }
                        if (message.startsWith("/reg ")) {
                            String token[] = message.split(" ");
                            if (token.length != 4) {
                                sendMsg("Неверный формат команды /reg");
                                continue;
                            }
                            if (server.getAuthenticatedProvider()
                                    .registration(this, token[1], token[2], token[3])) {
                                authenticated = true;
                                break;
                            }
                        }
                    }
                }
                while (authenticated) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMsg("/exitok");
                            break;
                        }
                        if (message.startsWith("/w ")) {
                            String[] parts = message.split(" ", 3);
                            if (parts.length >= 3) {
                                String recipient = parts[1];
                                String privateMsg = parts[2];
                                server.sendPrivateMessage(this, recipient, privateMsg);
                            }
                        }
                        if (message.startsWith("/kick ")){
                            if ("ADMIN".equals(role)){
                                String usernameToKick = message.split(" ")[1];
                                server.kickUser(this, usernameToKick);
                            } else {
                                sendMsg("У вас нет прав для этой команды");
                            }
                        }
                    } else {
                        server.broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    public void sendMsg(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void disconnect() {
        if (!authenticated) return;

        server.unsubscribe(this, false);
        forceClose();
    }
    private volatile boolean isBeingDisconnected = false;
    public void forceClose() {
        this.authenticated = false;

        // Закрываем потоки в правильном порядке
        try {
            if (out != null) {
                try {
                    out.writeUTF("/disconnected");
                    out.flush();
                } catch (IOException ignored) {
                    // Игнорируем ошибки принудительного закрытия
                }
            }
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException ignored) {}

            try {
                if (out != null) out.close();
            } catch (IOException ignored) {}

            try {
                if (socket != null) socket.close();
            } catch (IOException ignored) {}
        }
    }

}