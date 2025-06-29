package ru.otus.java.basic.homeworks.dbmaterials.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        try {
            logger.info("Запуск клиентского приложения");
            new Client("localhost", 8189);
        } catch (IOException e) {
            logger.error("Критическая ошибка в клиентском приложении", e);
            System.err.println("Не удалось запустить клиент: " + e.getMessage());
            System.exit(1);
        }
    }
}