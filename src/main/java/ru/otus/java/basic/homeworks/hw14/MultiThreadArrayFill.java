package ru.otus.java.basic.homeworks.hw14;

public class MultiThreadArrayFill {
    public static void main(String[] args) {
        int size = 100_000_000;
        double[] array = new double[size];
        long startTime = System.currentTimeMillis();

        Thread t1 = createThread(array, 0, size / 4);
        Thread t2 = createThread(array, size / 4, size / 2);
        Thread t3 = createThread(array, size / 2, size / 4 + size / 2);
        Thread t4 = createThread(array, size / 4 + size / 2, size);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Multi-thread (4 threads) time: " + (endTime - startTime) + " ms");
    }

    private static Thread createThread(final double[] array, final int start, final int end) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = start; i < end; i++) {
                    array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
                }
            }
        });
    }
}
