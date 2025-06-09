package ru.otus.java.basic.homeworks.hw19;

import ru.otus.java.basic.homeworks.hw19.server.AuthenticatedProvider;
import ru.otus.java.basic.homeworks.hw19.server.ClientHandler;
import ru.otus.java.basic.homeworks.hw19.server.Server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryAuthenticatedProvider implements AuthenticatedProvider {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    private class User {
        private String login;
        private String password;
        private String username;
        private String role;

        public User(String login, String password, String username) {
            this(login, password, username, USER); // По умолчанию USER
        }

        public User(String login, String password, String username, String role) {
            this.login = login;
            this.password = password;
            this.username = username;
            this.role = role;
        }
    }

    private List<User> users;
    private Server server;
    public InMemoryAuthenticatedProvider(Server server) {
        this.server = server;
        this.users = new CopyOnWriteArrayList<>();
        this.users.add(new User("admin", "admin", "admin", ADMIN)); // Первый пользователь - ADMIN
    }

    @Override
    public void initialize() {
        System.out.println("Сервис аунтентификации запущен: InMemory режим");
    }

    private String[] getAuthDataByLoginAndPassword(String login, String password) {
        for (User user : users) {
            if (user.login.equals(login.toLowerCase()) && user.password.equals(password)) {
                return new String[]{user.username, user.role};
            }
        }
        return null;
    }

    private boolean isLoginAlreadyExists(String login) {
        for (User user : users) {
            if (user.login.equals(login.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameAlreadyExists(String username) {
        for (User user : users) {
            if (user.username.equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String login, String password) {
        String[] authData = getAuthDataByLoginAndPassword(login, password);
        if (authData == null) {
            clientHandler.sendMsg("Некорректный логин/пароль");
            return false;
        }
        String authUsername = authData[0];
        String role = authData[1];

        if (server.isUsernameBusy(authUsername)) {
            clientHandler.sendMsg("Указанная учетная запись уже занята");
            return false;
        }

        clientHandler.setUsername(authUsername);
        clientHandler.setRole(role);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("Вы вошли в чат как: " + authUsername);
        return true;
    }

    @Override
    public boolean registration(ClientHandler clientHandler, String login, String password, String username) {
        if (login.length() < 3) {
            clientHandler.sendMsg("Логин должен быть 3+ символа");
            return false;
        }
        if (username.length() < 3) {
            clientHandler.sendMsg("Имя пользователя должна быть 3+ символа");
            return false;
        }
        if (password.length() < 3) {
            clientHandler.sendMsg("Пароль должен быть 3+ символа");
            return false;
        }
        if (isLoginAlreadyExists(login)) {
            clientHandler.sendMsg("Такой логин уже занят");
            return false;
        }
        if (isUsernameAlreadyExists(username)) {
            clientHandler.sendMsg("Такое имя пользователя уже занято");
            return false;
        }
        users.add(new User(login, password, username));
        clientHandler.setUsername(username);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/regok " + username);
        return true;
    }
}