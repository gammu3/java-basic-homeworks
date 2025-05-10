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

                        String[] parts = inputLine.split(" ");
                        if (parts.length != 3) {
                            out.writeUTF("Ошибка: неверный формат ввода. Используйте: число операция число");
                            continue;
                        }

                        try {
                            double num1 = Double.parseDouble(parts[0]);
                            double num2 = Double.parseDouble(parts[2]);
                            String operation = parts[1];
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
                                        out.writeUTF("Ошибка: деление на ноль");
                                        continue;
                                    }
                                    result = num1 / num2;
                                    break;
                                default:
                                    out.writeUTF("Ошибка: неизвестная операция");
                                    continue;
                            }

                            out.writeUTF("Результат: " + result);
                        } catch (NumberFormatException e) {
                            out.writeUTF("Ошибка: неверный формат чисел");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при работе с клиентом: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }
}
