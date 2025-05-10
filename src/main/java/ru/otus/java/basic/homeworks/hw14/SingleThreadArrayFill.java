package ru.otus.java.basic.homeworks.hw14;

public class SingleThreadArrayFill {
    public static void main(String[] args) {
        int size = 100_000_000;
        double[] array = new double[size];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < array.length; i++) {
            array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Single thread time: " + (endTime - startTime) + " ms");
    }
}
