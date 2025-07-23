package ru.otus.java.basic.homeworks.hw20;

public class Main {
    public static void main(String[] args) {
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        for (int i = 0; i < 10; i++) {
            appleBox1.addFruit(new Apple());
        }
        for (int i = 0; i < 5; i++) {
            orangeBox.addFruit(new Orange());
        }

        System.out.println("Вес первой коробки с яблоками: " + appleBox1.getWeight());
        System.out.println("Вес коробки с апельсинами: " + orangeBox.getWeight());

        System.out.println("Коробки равны по весу? " + appleBox1.compare(orangeBox));

        appleBox1.transferFruitsTo(appleBox2);
        System.out.println("После пересыпания:");
        System.out.println("Яблок в первой коробке: " + appleBox1.getCount());
        System.out.println("Яблок во второй коробке: " + appleBox2.getCount());
    }
}
