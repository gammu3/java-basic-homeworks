package ru.otus.java.basic.homeworks.hw16.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Scanner scanner;
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;

    public Client() throws IOException {
        scanner = new Scanner(System.in);
        socket = new Socket("localhost", 8189);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        try {
            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith("/")) {
                            if (message.equals("/exitok")) {
                                break;
                            }
                            if (message.startsWith("/logok ")) {
                                System.out.println("Вы подключились под ником: " + message.split(" ")[1]);
                                continue;
                            }
                            if (message.startsWith("/regok ")) {
                                System.out.println("Вы успешно зарегистрировались и подключились под ником: "
                                        + message.split(" ")[1]);
                                continue;
                            }
                        }
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
            }).start();

            while (true) {
                String message = scanner.nextLine();
                out.writeUTF(message);
                if (message.equals("/exit")) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
