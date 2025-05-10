package ru.otus.java.basic.homeworks.hw13.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientCalculator {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8091);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            String serverResponse = in.readUTF();
            System.out.println(serverResponse);

            while (true) {
                System.out.println("Введите выражение в формате 'число операция число' (например, 1 + 2)");
                System.out.println("Для выхода введите 'exit'");

                String userInput = scanner.nextLine();

                out.writeUTF(userInput);

                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }

                String response = in.readUTF();
                System.out.println(response);
            }
        } catch (UnknownHostException e) {
            System.err.println("Не удалось подключиться к серверу: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}
