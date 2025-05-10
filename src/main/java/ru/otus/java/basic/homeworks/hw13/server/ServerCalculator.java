package ru.otus.java.basic.homeworks.hw13.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalculator {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8091)) {
            System.out.println("Сервер запущен и ожидает подключений...");
            startServer(serverSocket);
        } catch (IOException e) {
            System.out.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }
    private static void startServer(ServerSocket serverSocket) {
        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                 DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {

                System.out.println("Клиент с портом " + clientSocket.getPort() + " подключился к серверу");
                out.writeUTF("Доступные операции: + (сложение), - (вычитание), * (умножение), / (деление)");

                while (true) {
                    String inputLine = in.readUTF();
                    if ("exit".equalsIgnoreCase(inputLine)) {
                        System.out.println("Клиент с портом " + clientSocket.getPort() + " отключился от сервера. Сервер запущен и ожидает подключений...");
                        break;
                    }

                    System.out.println("Получено от клиента: " + inputLine);
                    processClientRequest(out, inputLine);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при работе с клиентом: " + e.getMessage());
            }
        }
    }

    private static void processClientRequest(DataOutputStream out, String inputLine) throws IOException {
        String[] parts = inputLine.split(" ");
        if (parts.length != 3) {
            out.writeUTF("Ошибка: неверный формат ввода. Используйте: число операция число");
            return;
        }

        try {
            double num1 = Double.parseDouble(parts[0]);
            double num2 = Double.parseDouble(parts[2]);
            String operation = parts[1];

            String result = calculate(num1, num2, operation);
            out.writeUTF(result);
        } catch (NumberFormatException e) {
            out.writeUTF("Ошибка: неверный формат чисел");
        }
    }
    private static String calculate(double num1, double num2, String operation) {
        try {
            double result;
            switch (operation) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("Деление на ноль");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестная операция");
            }
            return "Результат: " + result;
        } catch (ArithmeticException | IllegalArgumentException e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
