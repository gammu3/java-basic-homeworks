package ru.otus.java.basic.homeworks.hw04;

import java.util.Scanner;

public class Box {
    private int high;
    private int width;
    private int length;
    private String color;
    private boolean access;
    private String content;
    static Scanner scanner = new Scanner(System.in);

    public Box(int high, int width, int length, String color, String content) {
        this.high = high;
        this.width = width;
        this.length = length;
        this.color = color;
        this.access = false;
        this.content = "";
    }

    public void info() {
        System.out.print("Коробка размером: " + this.high + "X" + this.width + "X" + this.length + ". Цвет: " + this.color + ".");
        if (this.access) {
            System.out.print(" Коробка открыта. ");
        } else {
            System.out.print(" Коробка закрыта. ");
        }
        if (this.content == "") {
            System.out.println("Внутри: Пусто");
        } else {
            System.out.println("Внутри: " + this.content);
        }
    }

    public void open() {
        this.access = true;
        System.out.println("Вы открыли коробку.");
    }

    public void close() {
        this.access = false;
        System.out.println("Вы закрыли коробку.");
    }

    public void recolor() {
        System.out.println("Введите новый цвет коробки:");
        this.color = (scanner.next());
    }

    public void add() {
        if (this.content != "" & !this.access) {
            System.out.println("Нельзя ничего положить. Коробка занята и закрыта!");
            return;
        }
        if (this.content != "") {
            System.out.println("Нельзя ничего положить. Коробка занята!");
            return;
        }
        if (!this.access) {
            System.out.println("Нельзя ничего положить. Коробка закрыта!");
            return;
        }
        System.out.println("Что вы хотите положить в коробку?");
        this.content = (scanner.next());
    }

    public void clear() {
        if (!this.access) {
            System.out.println("Нельзя ничего выкинуть. Коробка закрыта!");
            return;
        }
        if (this.content == "") {
            System.out.println("Нельзя ничего выкинуть. Коробка пуста!");
            return;
        }
        System.out.println("Предмет: " + this.content + " выброшен из коробки.");
        this.content = "";
    }
}
