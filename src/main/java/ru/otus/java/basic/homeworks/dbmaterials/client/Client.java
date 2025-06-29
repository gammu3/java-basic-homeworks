package ru.otus.java.basic.homeworks.dbmaterials.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    private final Scanner scanner;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private boolean connected = false;

    public Client(String host, int port) throws IOException {
        this.scanner = new Scanner(System.in);
        connectWithRetry(host, port);
    }

    private void connectWithRetry(String host, int port) throws IOException {
        IOException lastException = null;

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                logger.info("Попытка подключения {} к {}:{}", attempt, host, port);
                this.socket = new Socket();
                this.socket.connect(new java.net.InetSocketAddress(host, port), CONNECTION_TIMEOUT);
                this.out = new DataOutputStream(socket.getOutputStream());
                this.in = new DataInputStream(socket.getInputStream());
                this.connected = true;
                logger.info("Успешное подключение к серверу");

                startMessageListener();
                startUserInputHandler();
                return;

            } catch (IOException e) {
                lastException = e;
                logger.warn("Ошибка подключения (попытка {} из {}): {}", attempt, MAX_RETRIES, e.getMessage());

                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Прервано во время ожидания повторного подключения", ie);
                    }
                }
            } finally {
                if (!connected && socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        logger.debug("Ошибка при закрытии неудачного соединения", e);
                    }
                }
            }
        }

        throw new IOException("Не удалось подключиться к серверу после " + MAX_RETRIES + " попыток", lastException);
    }

    private void startMessageListener() {
        new Thread(() -> {
            try {
                while (connected) {
                    String message = in.readUTF();
                    System.out.println(message);
                    if (message.equals("/exitok")) {
                        logger.info("Получена команда завершения от сервера");
                        disconnect();
                        break;
                    }
                }
            } catch (IOException e) {
                if (connected) {
                    logger.error("Ошибка при чтении сообщения от сервера", e);
                    System.err.println("Ошибка соединения с сервером: " + e.getMessage());
                }
            } finally {
                disconnect();
            }
        }).start();
    }

    private void startUserInputHandler() {
        try {
            while (connected) {
                String message = scanner.nextLine();
                if (!connected) break;

                try {
                    out.writeUTF(message);
                    if (message.equals("/exit")) {
                        logger.info("Отправлена команда выхода");
                        break;
                    }
                } catch (IOException e) {
                    logger.error("Ошибка при отправке сообщения", e);
                    System.err.println("Не удалось отправить сообщение: " + e.getMessage());
                    break;
                }
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void close() {
        disconnect();
    }

    public void disconnect() {
        if (!connected) return;

        connected = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            logger.info("Соединение с сервером закрыто");
        } catch (IOException e) {
            logger.error("Ошибка при закрытии соединения", e);
        } finally {
            in = null;
            out = null;
            socket = null;
        }
    }
}