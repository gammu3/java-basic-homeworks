package ru.otus.java.basic.homeworks.hw02;

import java.util.Arrays;
import java.util.Scanner;


public class AppHomework02 {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        taskWithStar1();
        taskWithStar2();
        taskWithStar3();
        taskWithStar4();
    }

    private static void taskWithStar4() {
        reverseArray();
    }

    private static void reverseArray() {
        //Генерация входного массива заданной длинны со случайными целыми числами от 0 до 10
        int[] inputArray = new int[10];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = ((int) (Math.random() * 11) );
        }

        int[] outputArray = new int[inputArray.length];
        int indexOutArray=0;
        for (int i = inputArray.length-1; i >= 0; i--) {
            outputArray[indexOutArray]=inputArray[i];
            indexOutArray++;
        }
        System.out.println(Arrays.toString(inputArray) + " => " + Arrays.toString(outputArray));
    }

    private static void taskWithStar3() {
        Scanner scanner = new Scanner(System.in);
        int[] inputArray = {1, 56, 63, 69, 85, 96, 107, 118, 129, 130};

        int currentValue = 0;
        boolean checkLabel = true;
        currentValue = inputArray[0];
        while (true) {
            System.out.println("На что будем проверять массив: "+Arrays.toString(inputArray)+"?");
            System.out.println("Введите (1) для проверки по возрастанию или (2) для проверки по убыванию:");
            int typeOfChecking = scanner.nextInt();
            if (typeOfChecking == 1) {
                for (int i = 1; i < inputArray.length; i++) {
                    if ((currentValue < inputArray[i]) && checkLabel) {
                        currentValue = inputArray[i];
                    } else {
                        checkLabel = false;
                        break;
                    }
                }
                if (checkLabel) {
                    System.out.println("Исследуемый массив задан по возрастанию!");
                } else {
                    System.out.println("Исследуемый массив НЕ задан по возрастанию!");
                }
                break;
            } else if (typeOfChecking == 2) {
                for (int i = 1; i < inputArray.length; i++) {
                    if ((currentValue > inputArray[i]) && checkLabel) {
                        currentValue = inputArray[i];
                    } else {
                        checkLabel = false;
                        break;
                    }
                }
                if (checkLabel) {
                    System.out.println("Исследуемый массив задан по убыванию!");
                } else {
                    System.out.println("Исследуемый массив НЕ задан по убыванию!");
                }
                break;
            } else {
                System.out.println("Введён некорректный запрос!");
            }
        }
    }

    private static void taskWithStar2() {
        //Генерация входного массива заданной длинны со случайными целыми числами от -2 до 2
        int[] inputArray = new int[10];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = ((int) (Math.random() * 5) - 2);
        }
        //Ручной ввод массива:
//        int[] inputArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        int sumOfPrevElements = 0;
        int sumOfAllElements = 0;
        boolean labelOfEquals = true;

        for (int i = 0; i < 10; i++) {
            sumOfAllElements += inputArray[i];
        }
        for (int i = 0; i < 10; i++) {
            sumOfPrevElements += inputArray[i];
            if (i == 0) {
                System.out.print("[" + inputArray[i] + ", ");
            } else if (i != 9) {
                System.out.print(inputArray[i] + ", ");
            } else {
                System.out.print(inputArray[i] + "]");
            }
            if ((sumOfAllElements - sumOfPrevElements == sumOfPrevElements) && labelOfEquals) {
                labelOfEquals = false;
                System.out.print("| ");
            }
        }
    }

    private static void taskWithStar1() {
        //Генерация размера входных массивов случайными целыми числами от 1 до 10
        int sizeArray1 = ((int) (Math.random() * 10)+1);
        int sizeArray2 = ((int) (Math.random() * 10)+1);
        int sizeArray3 = ((int) (Math.random() * 10)+1);
        int sizeArray4 = ((int) (Math.random() * 10)+1);

        int maxSizeArray = Math.max(sizeArray1,sizeArray2);
        maxSizeArray=Math.max(maxSizeArray,sizeArray3);
        maxSizeArray=Math.max(maxSizeArray,sizeArray4);

        //Генерация входных массивов заданной длинны со случайными целыми числами от 0 до 10
        int[] inputArray1 = new int[sizeArray1];
        for (int i = 0; i < inputArray1.length; i++) {
            inputArray1[i] = ((int) (Math.random() * 11));
        }
        int[] inputArray2 = new int[sizeArray2];
        for (int i = 0; i < inputArray2.length; i++) {
            inputArray2[i] = ((int) (Math.random() * 11));
        }
        int[] inputArray3 = new int[sizeArray3];
        for (int i = 0; i < inputArray3.length; i++) {
            inputArray3[i] = ((int) (Math.random() * 11));
        }
        int[] inputArray4 = new int[sizeArray4];
        for (int i = 0; i < inputArray4.length; i++) {
            inputArray4[i] = ((int) (Math.random() * 11));
        }
        //Ручной ввод массивов:
//        int[] inputArray1 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
//        int[] inputArray2 = {1,1,1,1};
//        int[] inputArray3 = {1,1,1,1,1,1,1};
//        int[] inputArray4 = {1,1,1,1,1,1,1,1,1,1,1,1,1};

        //Проверка наполнения массивов
//        System.out.println(Arrays.toString(inputArray1));
//        System.out.println(Arrays.toString(inputArray2));
//        System.out.println(Arrays.toString(inputArray3));
//        System.out.println(Arrays.toString(inputArray4));

        sumArrayByElments(inputArray1, inputArray2, inputArray3, inputArray4,maxSizeArray);
    }

    private static void sumArrayByElments(int[] inputArray1, int[] inputArray2, int[] inputArray3, int[] inputArray4, int maxSizeArray) {

        int[] outputArray = new int[maxSizeArray];
        for (int i = 0; i < maxSizeArray; i++) {
            if ((inputArray1.length - 1) >= i) {
                outputArray[i] += inputArray1[i];
            }
            if (inputArray2.length - 1 >= i) {
                outputArray[i] += inputArray2[i];
            }
            if (inputArray3.length - 1 >= i) {
                outputArray[i] += inputArray3[i];
            }
            if (inputArray4.length - 1 >= i) {
                outputArray[i] += inputArray4[i];
            }
        }
        System.out.println("  "+Arrays.toString(inputArray1)+" +");
        System.out.println("+ "+Arrays.toString(inputArray2)+" +");
        System.out.println("+ "+Arrays.toString(inputArray3)+" +");
        System.out.println("+ "+Arrays.toString(inputArray4)+" =");
        System.out.println("= "+Arrays.toString(outputArray));
    }

    private static void task5() {
        //Генерация размера входного массива случайным целым числом от 2 до 10
        int sizeArray = ((int) (Math.random() * 10)+2);
        //Генерация входного массива заданной длинны со случайными целыми числами от 0 до 10
        int[] inputArray = new int[sizeArray];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = ((int) (Math.random() * 11));
        }
          //Ручной ввод:
//        int[] inputArray = {100, 4, 20, 8, 9, 23, 1, 45, 4};
        witchHalfOfMassiveBigger(inputArray);
    }

    private static void witchHalfOfMassiveBigger(int[] inputArray) {
        int sumFirstPartMassive = 0;
        int sumSecondPartMassive = 0;
        for (int i = 0; i < inputArray.length; i++) {
            if (i < inputArray.length / 2) {
                sumFirstPartMassive += inputArray[i];
            } else {
                sumSecondPartMassive += inputArray[i];
            }
        }
        if (sumFirstPartMassive > sumSecondPartMassive) {
            System.out.println("Сумма первой половины массива "+Arrays.toString(inputArray)+" больше второй половины.");
        } else {
            System.out.println("Сумма второй половины массива "+Arrays.toString(inputArray)+" больше первой половины.");
        }
        //Вывод полученных сумм для проверки:
//        System.out.println("Сумма первой половины = " + sumFirstPartMassive + ";");
//        System.out.println("Сумма второй половины = " + sumSecondPartMassive + ";");
    }


    private static void task4() {
        //Генерация размера входного массива случайным целым числом от 1 до 10
        int sizeArray = ((int) (Math.random() * 10)+1);
        //Генерация входного массива заданной длинны со случайными целыми числами от 0 до 10
        int[] inputArray = new int[sizeArray];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = ((int) (Math.random() * 11));
        }
        //Ручной ввод:
//        int[] inputArray = {1, 4, 8, 9, 23, 1, 45, 4, 7};
        //Генерация случайного целого числа от 0 до 10
        int numberToIncreaseArray = ((int) (Math.random() * 11));
        System.out.print("Массив: "+Arrays.toString(inputArray)+" после увеличения на "+numberToIncreaseArray+" = ");
        increaseEveryElmMassiveToNumber(inputArray, numberToIncreaseArray);
        //Проверка, что "исходный" то же поменялся, то есть ссылается на те же блоки памяти в Heap
//        System.out.println(Arrays.toString(inputArray));
    }

    private static void increaseEveryElmMassiveToNumber(int[] array, int NumberToIncrease) {
        for (int i = 0; i < array.length; i++) {
            array[i] += NumberToIncrease;
        }
        //Проверка, что поменялись блоки памяти в Heap
        System.out.println(Arrays.toString(array));
    }

    private static void task3() {
        //Генерация размера входного массива случайным целым числом от 1 до 10
        int sizeArray = ((int) (Math.random() * 10)+1);
        //Генерация входного массива заданной длинны со случайными целыми числами от 0 до 10
        int[] inputArray = new int[sizeArray];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = ((int) (Math.random() * 11));
        }
        //Ручной ввод:
//        int[] inputArray = {1, 4, 8, 9, 23, 1, 45, 4, 7};
        //Генерация случайного целого числа от 0 до 10
        int numberToFillArray = ((int) (Math.random() * 11));
        System.out.print(Arrays.toString(inputArray)+" => ");
        replaceAllElmMassiveWithNumber(inputArray, numberToFillArray);
        //Проверка, что "исходный" то же поменялся, то есть ссылается на те же блоки памяти в Heap
//        System.out.println(Arrays.toString(inputArray));
    }

    private static void replaceAllElmMassiveWithNumber(int[] array, int numberToFillArray) {
        for (int i = 0; i < array.length; i++) {
            array[i] = numberToFillArray;
        }
        //Проверка, что поменялись блоки памяти в Heap
        System.out.println(Arrays.toString(array));
    }

    private static void task2() {
        //Генерация размера входного массива случайным целым числом от 1 до 10
        int sizeArray = ((int) (Math.random() * 10)+1);
        //Генерация входного массива заданной длинны со случайными целыми числами от 0 до 10
        int[] inputArray = new int[sizeArray];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = ((int) (Math.random() * 11));
        }
        //Ручной ввод:
//        int[] inputArray = {1, 4, 8, 9, 23, 1, 45, 4, 7};
        sumOfElmMassiveMore5(inputArray);
    }

    private static void sumOfElmMassiveMore5(int[] inputArray) {
        int sumOfElements = 0;
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] > 5) {
                sumOfElements += inputArray[i];
            }
        }
        System.out.println("Сумма элементов массива "+Arrays.toString(inputArray)+", значение которых >5 = " + sumOfElements);
    }

    private static void task1() {
        System.out.println("Введите строку:");
        Scanner scanner = new Scanner(System.in);
        String wordToRepeat = scanner.nextLine();
        System.out.println("Введите количество повторений:");
        int countOfRepeat = scanner.nextInt();

//        Вариант с предопределённым словом и числом:
//        String wordToRepeat = "World";
//        int countOfRepeat = 5;
        printWordSeveralTimes(countOfRepeat, wordToRepeat);
    }

    private static void printWordSeveralTimes(int countOfRepeat, String wordToRepeat) {
        for (int i = 0; i < countOfRepeat; i++) {
            System.out.println(wordToRepeat);
        }
    }
}
