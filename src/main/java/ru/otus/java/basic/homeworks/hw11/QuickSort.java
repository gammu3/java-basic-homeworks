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


 //worked version
    private static void quickSort(int[] array, int lowerInd, int upperInd) {
        if (lowerInd < upperInd) {

            int pivot = array[upperInd]; // опорный элемент
            int i = lowerInd - 1; // индекс меньшего элемента

            for (int j = lowerInd; j < upperInd; j++) {
                // Если текущий элемент меньше или равен опорному
                if (array[j] <= pivot) {
                    i++;
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            int pi = i + 1;
            int temp = array[pi];
            array[pi] = array[upperInd];
            array[upperInd] = temp;

            // Рекурсивно сортируем элементы перед разбиением и после
            quickSort(array, lowerInd, pi - 1);
            quickSort(array, pi + 1, upperInd);
        }
    }







//assistents
//    private static void quickSort(int[] array, int lowerInd, int upperInd) {
//        if (lowerInd < upperInd) {
//            int currentSmallerElement = array[(lowerInd)];
//
//            do {
//                while (currentSmallerElement <= array[upperInd]) {
//                    upperInd--;
//                }
//                if (lowerInd < upperInd) {
//                    array[lowerInd] = array[upperInd];
//                    array[upperInd] = currentSmallerElement;
//                    lowerInd++;
//                }
//
//            } while (lowerInd <= upperInd);
//
//            // Рекурсивно сортируем элементы перед разбиением и после
//            quickSort(array, lowerInd, middle - 1);
//            quickSort(array, middle + 1, upperInd);
//        }
//    }
//
//    private static int partition(int[] array, int low, int high) {
//        int pivot = array[high]; // опорный элемент
//        int i = low - 1; // индекс меньшего элемента
//
//        for (int j = low; j < high; j++) {
//            // Если текущий элемент меньше или равен опорному
//            if (array[j] <= pivot) {
//                i++;
//                swap(array, i, j);
//            }
//        }
//
//        // Помещаем опорный элемент на правильную позицию
//        swap(array, i + 1, high);
//        return i + 1;
//    }
//
//    // Вспомогательный метод для обмена элементов
//    private static void swap(int[] array, int i, int j) {
//        int temp = array[i];
//        array[i] = array[j];
//        array[j] = temp;
//    }

}
