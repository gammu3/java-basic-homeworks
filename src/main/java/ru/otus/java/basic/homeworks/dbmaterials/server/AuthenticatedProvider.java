package ru.otus.java.basic.homeworks.dbmaterials.server;

public interface AuthenticatedProvider {
//    void initialize();
    boolean authenticate(ClientHandler clientHandler, String login, String password);
    boolean registration(ClientHandler clientHandler, String login, String password, String username);
}