package ru.otus.java.basic.homeworks.hw23;

import java.util.Arrays;

public class ArrayUtils {

    public static int[] getElementsAfterLastOne(int[] array) {
        if (array == null || array.length == 0) {
            throw new RuntimeException("Input array is empty or null");
        }

        int lastOneIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                lastOneIndex = i;
            }
        }

        if (lastOneIndex == -1) {
            throw new RuntimeException("Input array does not contain any ones");
        }

        if (lastOneIndex == array.length - 1) {
            return new int[0];
        }

        return Arrays.copyOfRange(array, lastOneIndex + 1, array.length);
    }

    public static boolean isArrayValid(int[] array) {
        if (array == null || array.length < 2) {
            return false;
        }

        boolean hasOne = false;
        boolean hasTwo = false;

        for (int num : array) {
            if (num == 1) {
                hasOne = true;
            } else if (num == 2) {
                hasTwo = true;
            } else {
                return false;
            }
        }

        return hasOne && hasTwo;
    }
}
