package ru.otus.java.basic.homeworks.dbmaterials.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.basic.homeworks.dbmaterials.entity.Material;
import ru.otus.java.basic.homeworks.dbmaterials.service.MaterialService;
import ru.otus.java.basic.homeworks.dbmaterials.service.MaterialServiceImpl;

import java.io.*;
import java.net.Socket;
import java.util.List;



public class ClientHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private UserRole role;
    private boolean authenticated;
    private String username;
    private MaterialService materialService;


    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.materialService = new MaterialServiceImpl(server.getConnection());

        new Thread(() -> {
            try {
                handleClient();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    private void handleClient() throws IOException {
        while (!authenticated) {
            sendMsg("Для работы необходимо выполнить аутентификацию '/log email password' или регистрацию '/reg email password username'");
            sendMsg("Введите /help для списка команд");
            String message = in.readUTF();

            if (message.equals("/help")) {
                showHelp(false); // Показываем базовые команды для неаутентифицированных пользователей
            } else if (message.startsWith("/log ")) {
                handleLogin(message);
            } else if (message.startsWith("/reg ")) {
                handleRegistration(message);
            }
        }

        while (authenticated) {
            String message = in.readUTF();
            if (message.equals("/exit")) {
                break;
            } else if (message.equals("/help")) {
                showHelp(true); // Показываем полный список команд для аутентифицированных пользователей
            } else if (message.equals("/list")) {
                handleListMaterials();
            } else if (message.startsWith("/view ")) {
                handleViewMaterial(message);
            } else if (message.startsWith("/add ")) {
                handleAddMaterial(message);
            } else if (message.startsWith("/delete ")) {
                handleDeleteMaterial(message);
            } else if (message.startsWith("/update ")) {
                handleUpdateMaterial(message);
            }else{
                sendMsg("Неверный формат команды. Для справки введите /help");
            }

        }
    }

    private void handleLogin(String message) {
        String[] tokens = message.split(" ");
        if (tokens.length != 3) {
            sendMsg("Неверный формат команды /log");
            return;
        }
        if (server.getAuthenticatedProvider().authenticate(this, tokens[1], tokens[2])) {
            authenticated = true;
            sendMsg("Аутентификация успешна. Ваша роль: " + role);
            sendMsg("Введите /help для списка команд");
        }
    }

    private void handleRegistration(String message) {
        String[] tokens = message.split(" ");
        if (tokens.length != 4) {
            sendMsg("Неверный формат команды /reg");
            return;
        }
        if (server.getAuthenticatedProvider().registration(this, tokens[1], tokens[2], tokens[3])) {
            authenticated = true;
            sendMsg("Регистрация успешна. Ваша роль: " + role);
        }
    }

    private void handleListMaterials() {
        List<Material> materials = materialService.getAllMaterials();
        if (materials.isEmpty()) {
            sendMsg("Нет доступных материалов");
            return;
        }
        for (Material material : materials) {
            sendMsg(material.getId() + ": " + material.getName());
        }
    }

    private void handleViewMaterial(String message) {
        try {
            int id = Integer.parseInt(message.split(" ")[1]);
            Material material = materialService.getMaterialById(id);
            if (material != null) {

                sendMsg("Материал #" + material.getId() + ": " + material.getName());
                sendMsg("Плотность: " + material.getDensity());
                sendMsg("Модуль Юнга: " + material.getYoungModulus());
                sendMsg("Коэффициент Пуассона: " + material.getPoissonRatio());

                // Предлагаем сохранить в файл
                sendMsg("Хотите сохранить эти данные в файл? (yes/no)");
                String response = in.readUTF().toLowerCase();

                if (response.equals("yes") || response.equals("y")) {
                    saveMaterialToFile(material);
                }
                sendMsg("Введите /help для списка команд");
            } else {
                sendMsg("Материал с ID " + id + " не найден");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            sendMsg("Неверный формат команды /view. Используйте: /view id");
        } catch (IOException e) {
            sendMsg("Ошибка при чтении ответа: " + e.getMessage());
        }
    }

    private void handleAddMaterial(String message) {
        if (role != UserRole.ADMIN) {
            sendMsg("Ошибка: недостаточно прав");
            return;
        }

        try {
            String[] parts = message.split(" ", 5);
            if (parts.length < 5) {
                sendMsg("Неверный формат команды /add. Используйте: /add name density youngModulus poissonRatio");
                return;
            }

            String name = parts[1];
            double density = Double.parseDouble(parts[2]);
            double youngModulus = Double.parseDouble(parts[3]);
            double poissonRatio = Double.parseDouble(parts[4]);

            Material material = new Material(0, name, density, youngModulus, poissonRatio);
            if (materialService.addMaterial(material)) {
                sendMsg("Материал успешно добавлен с ID: " + material.getId());
            } else {
                sendMsg("Ошибка при добавлении материала");
            }
        } catch (NumberFormatException e) {
            sendMsg("Ошибка: числовые параметры должны быть в правильном формате");
        } catch (Exception e) {
            sendMsg("Неверный формат команды /add. Используйте: /add name density youngModulus poissonRatio");
        }
    }

    private void handleDeleteMaterial(String message) {
        if (role != UserRole.ADMIN) {
            sendMsg("Ошибка: недостаточно прав");
            return;
        }

        try {
            int id = Integer.parseInt(message.split(" ")[1]);
            if (materialService.deleteMaterial(id)) {
                sendMsg("Материал с ID " + id + " успешно удален");
            } else {
                sendMsg("Материал с ID " + id + " не найден");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            sendMsg("Неверный формат команды /delete. Используйте: /delete id");
        }
    }

    private void handleUpdateMaterial(String message) {
        if (role != UserRole.ADMIN) {
            sendMsg("Ошибка: недостаточно прав");
            return;
        }

        try {
            String[] parts = message.split(" ", 6);
            int id = Integer.parseInt(parts[1]);
            String name = parts[2];
            double density = Double.parseDouble(parts[3]);
            double youngModulus = Double.parseDouble(parts[4]);
            double poissonRatio = Double.parseDouble(parts[5]);

            Material material = new Material(id, name, density, youngModulus, poissonRatio);
            if (materialService.updateMaterial(material)) {
                sendMsg("Материал с ID " + id + " успешно обновлен");
            } else {
                sendMsg("Материал с ID " + id + " не найден");
            }
        } catch (Exception e) {
            sendMsg("Неверный формат команды /update. Используйте: /update id name density youngModulus poissonRatio");
        }
    }

    public void sendMsg(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (authenticated) {
            server.unsubscribe(this);
        }
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showHelp(boolean authenticated) {
        sendMsg("=== Список доступных команд ===");
        if (!authenticated) {
            sendMsg("/log email password - аутентификация");
            sendMsg("/reg email password username - регистрация");
            return;
        }

        sendMsg("/list - показать список всех материалов");
        sendMsg("/view id - показать детали материала с указанным ID");
        sendMsg("/exit - выход из системы");

        if (role == UserRole.ADMIN) {
            sendMsg("=== Команды администратора ===");
            sendMsg("/add name density youngModulus poissonRatio - добавить новый материал");
            sendMsg("/delete id - удалить материал");
            sendMsg("/update id name density youngModulus poissonRatio - обновить материал");
        }
    }

    private void saveMaterialToFile(Material material) {
        String fileName = "material_" + material.getId() + "_" + System.currentTimeMillis() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("=== Характеристики материала ===");
            writer.println("ID: " + material.getId());
            writer.println("Название: " + material.getName());
            writer.println("Плотность: " + material.getDensity() + " кг/м³");
            writer.println("Модуль Юнга: " + material.getYoungModulus() + " Па");
            writer.println("Коэффициент Пуассона: " + material.getPoissonRatio());

            sendMsg("Данные сохранены в файл: " + fileName);
        } catch (IOException e) {
            sendMsg("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public void setRole(UserRole role) { this.role = role; }
}