package ru.otus.java.basic.homeworks.hw12;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TextRedactor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File workedDirectory = new File("src/main/java/ru/otus/java/basic/homeworks/hw12");

        if (!workedDirectory.exists() || !workedDirectory.isDirectory()) {
            System.out.println("Рабочая директория не существует или не является директорией");
            return;
        }

        FilenameFilter textFilesFilter = (dir, name) -> name.toLowerCase().endsWith(".txt");
        File[] textFiles = workedDirectory.listFiles(textFilesFilter);

        if (textFiles == null || textFiles.length == 0) {
            System.out.println("В директории нет текстовых файлов (.txt)");
            return;
        }

        while (true) {
            System.out.println("Список текстовых файлов в корневом каталоге проекта:");
            for (File file : textFiles) {
                System.out.println(file.getName());
            }

//            File selectedFile = selectFile(scanner, textFiles);
            String fileName = selectFile(scanner, textFiles);

            if (fileName == null) {
                // Пользователь ввел /exit
                System.out.println("Работа программы завершена.");
                return;
            }

            File selectedFile = new File(workedDirectory, fileName);

            printFileContent(selectedFile);
            workWithFile(selectedFile, scanner);

            if (!askToContinue(scanner)) {
                System.out.println("Работа программы завершена.");
                break;
            }

        }
    }

    public static void printFileContent(File selectedFile) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(selectedFile))) {
            System.out.println("\nСодержимое файла " + selectedFile.getName() + ":");
            System.out.println("----------------------------------------");

            int n = bis.read();
            while (n != -1) {
                System.out.print((char) n);
                n = bis.read();
            }
            System.out.println();
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static void workWithFile(File selectedFile, Scanner scanner) {
        System.out.println("\nВведите строку для добавления в файл:");
        System.out.println("- для переноса строки введите '/nl'");
        System.out.println("- для сохранения и завершения введите '/save'");
        System.out.println("- для печати файла введите '/print'");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("/save".equalsIgnoreCase(input)) {
                break;
            }
            if ("/print".equalsIgnoreCase(input)) {
                printFileContent(selectedFile);
                continue;
            }

            try (BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(selectedFile, true))) {

                if ("/nl".equalsIgnoreCase(input)) {
                    bos.write(System.lineSeparator().getBytes());
                } else {
                    byte[] buffer = input.getBytes(StandardCharsets.UTF_8);
                    bos.write(buffer);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        }
        System.out.println("Работа с файлом завершена.");
    }

    private static String selectFile(Scanner scanner, File[] textFiles) {
        while (true) {
            System.out.print("\nВведите имя файла (с .txt или без), с которым хотите работать (/exit - выход): ");
            String input = scanner.nextLine().trim();

            if ("/exit".equalsIgnoreCase(input)) {
                return null;
            }

            for (File file : textFiles) {
                if (file.getName().equalsIgnoreCase(input) ||
                        file.getName().equalsIgnoreCase(input + ".txt")) {
                    return file.getName();
                }
            }

            System.out.println("Файл с таким именем не найден. Попробуйте еще раз.");
        }
    }

    private static boolean askToContinue(Scanner scanner) {
        while (true) {
            System.out.println("\nХотите продолжить работу?");
            System.out.println("'y' - Да, выбрать новый файл");
            System.out.println("'n' - Нет, закончить работу");
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if ("n".equalsIgnoreCase(input)) {
                return false;
            }
            if ("y".equalsIgnoreCase(input)) {
                return true;
            }
            System.out.println("Некорректный ввод. Пожалуйста, введите 'y' или 'n'.");
        }
    }
}


