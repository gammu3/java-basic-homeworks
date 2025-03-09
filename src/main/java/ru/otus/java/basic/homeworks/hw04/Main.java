package ru.otus.java.basic.homeworks.hw04;

public class Main {
    public static void main(String[] args) {
        User[] users = {
                new User("Петров", "Василий", "Иванович", 1990, "vpetrov@gmail.com"),
                new User("Иванов", "Станислав", "Петрович", 1980, "sivanov@gmail.com"),
                new User("Сидоров", "Сергей", "Михайлович", 1984, "ssidorov@gmail.com"),
                new User("Завьялов", "Иван", "Сергеевич", 1992, "izavyalov@gmail.com"),
                new User("Кук", "Пётр", "Васильевич", 1988, "pkuk@gmail.com"),
                new User("Захаров", "Николай", "Павлович", 1987, "nzakharov@gmail.com"),
                new User("Пантелеев", "Олег", "Андреевич", 1974, "opanteleev@gmail.com"),
                new User("Тарасов", "Павел", "Дмитриевич", 1986, "ptarasov@gmail.com"),
                new User("Давыдов", "Антон", "Алексеевич", 1998, "adavudov@gmail.com"),
                new User("Асафьев", "Андрей", "Антонович", 1988, "aasaviev@gmail.com")
        };

        for (int i = 0; i < users.length; i++) {
            if ((2025 - users[i].getYear()) > 40) {
                users[i].info();
            }

        }
    }
}
