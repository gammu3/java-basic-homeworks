package hw10;

import java.util.*;

public class PhoneBook {
    private final Map<String, Set<String>> nameToPhoneNumbersMap;

    public PhoneBook() {
        this.nameToPhoneNumbersMap = new HashMap<>();
    }

    public void add(String name, String phoneNumber) {
        Set<String> phoneNumbers = nameToPhoneNumbersMap.computeIfAbsent(name, k -> new HashSet<>());
        phoneNumbers.add(phoneNumber);
    }

    public void find(String name) {
        boolean isFindName = false;
        Set<String> phoneNumbersForFindName = new HashSet<>();
        for (String currentName : nameToPhoneNumbersMap.keySet()) {
            if (currentName.contains(name)) {
                isFindName = true;
                phoneNumbersForFindName.add(nameToPhoneNumbersMap.get(currentName).toString());
            }
        }
        if (isFindName) {
            System.out.println("Номера пользователя " + name + ": [" + Arrays.toString(phoneNumbersForFindName
                    .toArray()).replace("[", "").replace("]", "")
                    + "]");
        } else {
            System.out.println("Пользователя " + name + " нет в телефонной книге.");
        }
    }

    public void containsPhoneNumber(String phoneNumber) {
        boolean isFindPhoneNumber = false;
        for (Set<String> currentPhoneNumbers : nameToPhoneNumbersMap.values()) {
            if (currentPhoneNumbers.contains(phoneNumber)) {
                isFindPhoneNumber = true;
                break;
            }
        }
        if (isFindPhoneNumber) {
            System.out.println("Телефонный номер " + phoneNumber + " есть в телефонной книге.");
        } else {
            System.out.println("Телефонный номер " + phoneNumber + " отсутствует в телефонной книге.");
        }
    }
}
