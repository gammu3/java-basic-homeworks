package hw08;

public class AppMain {
    public static void main(String[] args) {
        String[][] array = {
                {"1", "2", "3", "4"},
                {"5", "6", "1", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };
        arrayCheck(array);
    }

    public static void arrayCheck(String[][] array) {
        try {
            System.out.println("Сумма массива: " + sumOfElementsArray(array));
        } catch (AppArraySizeException e) {
            System.err.println("AppArraySizeException: " + e.getMessage());
        } catch (AppArrayDataException e) {
            System.err.println("AppArrayDataException : " + e.getMessage());
        }
    }

    public static int sumOfElementsArray(String[][] array) throws AppArraySizeException, AppArrayDataException {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            if (array.length != 4 || array[i].length != 4) {
                throw new AppArraySizeException("Неверный размер массива. Ожидается 4x4.");
            }
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new AppArrayDataException("Неверное значение в [" + (i + 1) + "," + (j + 1) + "]");
                }
            }
        }
        return sum;
    }
}
