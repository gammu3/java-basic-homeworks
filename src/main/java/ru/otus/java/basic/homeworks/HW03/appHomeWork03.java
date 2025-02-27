package ru.otus.java.basic.homeworks.HW03;

public class appHomeWork03 {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
    }

    private static void task1() {
        int[][] arrayInput2D = {{1, 1, 1}, {-2, 0, 2}};
        int resultOfSum = sumOfPositiveElements(arrayInput2D);
        System.out.println(resultOfSum);
    }

    private static int sumOfPositiveElements(int[][] arrayInput2D) {
        int sum = 0;
        for (int i = 0; i < arrayInput2D.length; i++) {
            for (int j = 0; j < arrayInput2D[i].length; j++) {
                if (arrayInput2D[i][j] > 0) {
                    sum += arrayInput2D[i][j];
                }
            }
        }
        return sum;
    }

    private static void task2() {
        int sizeOfSquareArray = 5;
        printSquareOfStars(sizeOfSquareArray);
    }

    private static void printSquareOfStars(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    private static void task3() {
        int[][] array2D = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
        zerosDiagonalsOf2DArray(array2D);
    }

    private static void zerosDiagonalsOf2DArray(int[][] arrayInput2D) {
        for (int i = 0; i < arrayInput2D.length; i++) {
            for (int j = 0; j < arrayInput2D[i].length; j++) {
                if (i == j) {
                    arrayInput2D[i][j] = 0;
                }
                if (i == (arrayInput2D[i].length - 1) - j) {
                    arrayInput2D[i][j] = 0;
                }
                System.out.print(arrayInput2D[i][j] + " ");
            }
            System.out.println();
        }
    }


    private static void task4() {
        int[][] arrayInput2D = {{-51, 10, 1}, {-2, 0, 20}};
        int maximumElement = findMax(arrayInput2D);
    }

    private static int findMax(int[][] array) {
        int maxElm = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (maxElm < array[i][j]) {
                    maxElm = array[i][j];
                }
            }
        }
        return maxElm;
    }

    private static void task5() {
        int[][] arrayInput2D = {{1, 1, 1, 5, 1}, {1, 2, 3, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
        int sumOfElementsSecondRow = findSumOfElementsSecondRowOfArray(arrayInput2D);
    }

    private static int findSumOfElementsSecondRowOfArray(int[][] array) {
        if (array.length < 2) {
            return -1;
        }
        int sum = 0;
        for (int i = 0; i < array[1].length; i++) {
            sum += array[1][i];
        }
        return sum;
    }
}

