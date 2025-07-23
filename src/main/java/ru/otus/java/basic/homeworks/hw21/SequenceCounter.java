package ru.otus.java.basic.homeworks.hw21;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SequenceCounter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();

        System.out.print("Введите искомую последовательность символов: ");
        String sequence = scanner.nextLine();

        try {
            int count = countSequenceOccurrences(fileName, sequence);
            System.out.println("Последовательность '" + sequence + "' встречается " + count + " раз(а)");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        scanner.close();
    }

    public static int countSequenceOccurrences(String fileName, String sequence) throws IOException {
        if (sequence.isEmpty()) {
            return 0;
        }

        int count = 0;
        int sequenceLength = sequence.length();
        char[] buffer = new char[1024];
        StringBuilder window = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                for (int i = 0; i < charsRead; i++) {
                    window.append(buffer[i]);

                    if (window.length() > sequenceLength) {
                        window.deleteCharAt(0);
                    }

                    if (window.length() == sequenceLength &&
                            window.toString().equals(sequence)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
