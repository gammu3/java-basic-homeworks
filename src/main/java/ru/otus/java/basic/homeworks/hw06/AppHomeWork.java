package ru.otus.java.basic.homeworks.hw06;

public class AppHomeWork {
    public static void main(String[] args) {
        Cat[] cats = {
                new Cat("Рыжик", 10),
                new Cat("Персик", 100),
                new Cat("Мурзик", 15),
        };

        Plate plate1 = new Plate(40);

        for (Cat cat : cats) {
            cat.eat(plate1);
            cat.infoSatiety();
        }
    }
}
