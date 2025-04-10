package hw09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppMain {
    public static void main(String[] args) {
        //Task 1
        ArrayList<Integer> array = generateArrayOfRange(3, 7);
        System.out.println("Последовательность: " + array);


        //Task 2
        List<Integer> numbers = List.of(1, 6, 3, 8, 10, 2, 7);
        int sum = sumGreaterThanFive(numbers);
        System.out.println("Сумма чисел больше 5: " + sum);


        //Task 3
        List<Integer> array1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        fillArrayWithNumber(array1, 42);
        System.out.println(array1);


        //Task 4
        List<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        incrementOftListElements(10, numbers2);
        System.out.println(numbers2);


        //Task 5
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иван", 30));
        employees.add(new Employee("Мария", 25));
        employees.add(new Employee("Петр", 40));
        employees.add(new Employee("Ольга", 22));

        //Task 5_1
        System.out.println("Имена сотрудников: " + getEmployeeNames(employees));

        //Task 5_2
        int minAge = 28;
        System.out.println("Сотрудники старше " + minAge + ": " +
                getEmployeeNames(filterEmployeesByMinAge(employees, minAge)));

        //Task 5_3
        int minAverageAge = 50;
        if (isAverageAgeAbove(employees, minAverageAge)) {
            System.out.println("Средний возраст больше " + minAverageAge);
        } else {
            System.out.println("Средний возраст меньше " + minAverageAge);
        }


        //Task 5_4
        Employee young = getYoungestEmployee(employees);
        System.out.println("Самый молодой сотрудник: " + young.getName());
    }

    public static ArrayList<Integer> generateArrayOfRange(int min, int max) {
        ArrayList<Integer> ArraySequence = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            ArraySequence.add(i);
        }
        return ArraySequence;
    }


    public static int sumGreaterThanFive(List<Integer> numbers) {
        int sum = 0;
        for (int num : numbers) {
            if (num > 5) {
                sum += num;
            }
        }
        return sum;
    }

    public static void fillArrayWithNumber(List<Integer> list, int number) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                list.set(i, number);
            }
        }
    }

    public static void incrementOftListElements(int increment, List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                list.set(i, list.get(i) + increment);
            } else {
                throw new IllegalArgumentException("Элемент " + i + " равен null он не может быть заменён. ");
            }

        }
    }

    public static List<String> getEmployeeNames(List<Employee> employees) {
        List<String> names = new ArrayList<>();
        isAnyElementsOfNull(employees);
        for (Employee emp : employees) {
            names.add(emp.getName());
        }
        return names;
    }

    public static List<Employee> filterEmployeesByMinAge(List<Employee> employees, int minAge) {
        List<Employee> filteredNames = new ArrayList<>();
        isAnyElementsOfNull(employees);
        for (Employee emp : employees) {
            if (emp.getAge() >= minAge) {
                filteredNames.add(emp);
            }
        }
        return filteredNames;
    }


    public static boolean isAverageAgeAbove(List<Employee> employees, int minAverageAge) {
        isAnyElementsOfNull(employees);

        int totalAge = 0;
        for (Employee emp : employees) {
            totalAge += emp.getAge();
        }

        int averageAge = totalAge / employees.size();
        return averageAge > minAverageAge;
    }


    public static Employee getYoungestEmployee(List<Employee> employees) {
        isAnyElementsOfNull(employees);

        Employee youngest = employees.get(0);
        for (Employee emp : employees) {
            if (emp.getAge() < youngest.getAge()) {
                youngest = emp;
            }
        }
        return youngest;
    }

    public static void isAnyElementsOfNull(List<Employee> employees) {
        for (Employee emp : employees) {
            if (emp.getName() == null) {
                throw new IllegalArgumentException("Имя одного из рабочих равно null.");
            }
            if (emp.getName().isBlank()) {
                throw new IllegalArgumentException("Имя одного из рабочих не задано.");
            }
        }
    }

}
