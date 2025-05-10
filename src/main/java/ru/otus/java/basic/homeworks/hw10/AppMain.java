package ru.otus.java.basic.homeworks.hw10;

public class AppMain {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        phoneBook.add("Иванов Иван Иванович", "1234567890");
        phoneBook.add("Иванов Петр Петрович", "0987654321");
        phoneBook.add("Петров Петр Петрович", "5555555555");
        phoneBook.add("Иванов Иван Иванович", "1111111111");

        phoneBook.find("Иванов Иван Иванович");
        phoneBook.find("Иванов");
        phoneBook.find("Петр");
        phoneBook.find("Сидоров");

        phoneBook.containsPhoneNumber("5555555555");
        phoneBook.containsPhoneNumber("0000000000");

    }
}
