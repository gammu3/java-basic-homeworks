package ru.otus.java.basic.homeworks.hw01;

import java.util.Scanner;

public class AppHomeWork01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберете метод для запуска:");
        System.out.println("1) greetings");
        System.out.println("2) checkSign");
        System.out.println("3) selectColor");
        System.out.println("4) compareNumbers");
        System.out.println("5) addOrSubtractAndPrint");
        int NumberOfMethodeRequested = scanner.nextInt();

//      Вариант 1 (Соответствует заданию и текущим урокам)
        if (NumberOfMethodeRequested == 1) {
            greetings();
        } else if (NumberOfMethodeRequested == 2) {
            int a = (int) (Math.random() * 200) - 100;
            int b = (int) (Math.random() * 200) - 100;
            int c = (int) (Math.random() * 200) - 100;
            checkSign(a, b, c);
        } else if (NumberOfMethodeRequested == 3) {
            selectColor();
        } else if (NumberOfMethodeRequested == 4) {
            compareNumbers();
        } else if (NumberOfMethodeRequested == 5) {
            int initValue = (int) (Math.random() * 200) - 100;
            int delta = (int) (Math.random() * 200) - 100;
            // Делаю случайное число в диапазоне [-10 : 10]
            int randomIncrement = (int) ((Math.random() * 20) + 1) - 10;
            boolean increment;
//      В зависимости от знака числа присваиваю значение переменной increment.
//      Таким образом получаю случайное значение булевой переменной.
//      Использовал switch, так как были в начале проблемы в случае запроса метода addOrSubtractAndPrint
//      Студия ругалась на if внутри общего if->else if
//      Вроде решил, но строка ниже # 39 if (randomIncrement > 0) { всё равно выделяется жёлтым и выдаёт warning при commit.
            if (randomIncrement > 0) {
                increment = true;
            } else {
                increment = false;
            }
            addOrSubtractAndPrint(initValue, delta, increment);
        } else {
            System.out.println("Неверный запрос");
        }
//        Вариант 2 (с дополнением от задания)
//        switch (NumberOfMethodeRequested) {
//            case (1):
//                greetings();
//                break;
//            case (2):
//                int a = (int) (Math.random() * 200) - 100;
//                int b = (int) (Math.random() * 200) - 100;
//                int c = (int) (Math.random() * 200) - 100;
//                checkSign(a, b, c);
//                break;
//            case (3):
//                selectColor();
//                break;
//            case (4):
//                compareNumbers();
//                break;
//            case (5):
//                int initValue = (int) (Math.random() * 200) - 100;
//                int delta = (int) (Math.random() * 200) - 100;
//                int randomIncrement = (int) ((Math.random() * 20) + 1) - 10;
//                boolean increment = false;
//                if (randomIncrement <= 0) {
//                    increment = false;
//                } else if ((randomIncrement > 0)) {
//                    increment = true;}
//                addOrSubtractAndPrint(initValue, delta, increment);
//                break;
//            default:
//                System.out.println("Неверный запрос");
//                break;
//        }
    }

    public static void greetings() {
        System.out.println("Hello");
        System.out.println("World");
        System.out.println("from");
        System.out.println("Java");
    }

    public static void checkSign(int a, int b, int c) {
        int sum = a + b + c;
        if (sum >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

    public static void selectColor() {
        int data = (int) (Math.random() * 100) - 50;
        if (data <= 10) {
            System.out.println("Красный");
        } else if (data <= 20) {
            System.out.println("Жёлтый");
        } else {
            System.out.println("Зелёный");
        }
    }

    public static void compareNumbers() {
        int a = (int) (Math.random() * 200) - 100;
        int b = (int) (Math.random() * 200) - 100;
        if (a >= b) {
            System.out.println("a>=b");
        } else {
            System.out.println("a<b");
        }
    }

    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
//        Вариант 1 (соответствует заданию):
        if (increment) {
            System.out.println(initValue + delta);
        } else {
            System.out.println(initValue - delta);
        }
//        Вариант 2 (с дополнением от задания):
//        int res;
//        if (increment) {
//            res=initValue - delta;
//        } else {
//            res=initValue + delta;
//        }
//        System.out.println("Передано значение - " + increment + ", поэтому результат = "+ res);

    }
}
