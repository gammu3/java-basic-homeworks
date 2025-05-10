package ru.otus.java.basic.homeworks.hw11;

public class QuickSort {
    public static void main(String[] args) {
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10000);
        }
        printArray(array);

        quickSort(array);

        printArray(array);
    }

    private static void printArray(int[] array) {
        for (int j : array) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void quickSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int leftInitial, int rightInitial) {
        int leftMarker = leftInitial;
        int rightMarker = rightInitial;
        int middleElement = array[(leftMarker + rightMarker) / 2];
        do {
            while (array[leftMarker] < middleElement) {
                leftMarker++;
            }
            while (array[rightMarker] > middleElement) {
                rightMarker--;
            }
            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    int tmp = array[leftMarker];
                    array[leftMarker] = array[rightMarker];
                    array[rightMarker] = tmp;
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);
        if (leftMarker < rightInitial) {
            quickSort(array, leftMarker, rightInitial);
        }
        if (leftInitial < rightMarker) {
            quickSort(array, leftInitial, rightMarker);
        }
    }
}
