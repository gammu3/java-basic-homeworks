package ru.otus.java.basic.homeworks.hw22;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCPrinter {
    private static final int PRINT_COUNT = 5;
    private static Lock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();
    private static volatile char currentChar = 'A';

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(() -> printLetter('A', conditionA, conditionB));
        executor.execute(() -> printLetter('B', conditionB, conditionC));
        executor.execute(() -> printLetter('C', conditionC, conditionA));
        executor.shutdown();

    }

    private static void printLetter(char letter, Condition currentCondition, Condition nextCondition) {
        for (int i = 0; i < PRINT_COUNT; i++) {
            lock.lock();
            try {
                while (currentChar != letter) {
                    currentCondition.await();
                }
                System.out.print(letter);
                currentChar = (char) (letter == 'C' ? 'A' : letter + 1);
                nextCondition.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}