package ru.otus.java.basic.homeworks.hw10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        Set<String> phoneNumbersForFindName = new HashSet<>();
        Set<Map.Entry<String, Set<String>>> entries = nameToPhoneNumbersMap.entrySet();
        for (Map.Entry<String, Set<String>> currentName : entries) {
            if (currentName.getKey().contains(name)) {
                phoneNumbersForFindName.add(String.valueOf(currentName.getValue()).replace("[", "").replace("]", ""));
            }

        }
        if (phoneNumbersForFindName.isEmpty()) {
            System.out.println("Пользователя " + name + " нет в телефонной книге.");
            return;
        }
        System.out.println("Номера пользователя " + name + ": " + phoneNumbersForFindName);
    }

    public void containsPhoneNumber(String phoneNumber) {

        for (Set<String> currentPhoneNumbers : nameToPhoneNumbersMap.values()) {
            if (currentPhoneNumbers.contains(phoneNumber)) {
                System.out.println("Телефонный номер " + phoneNumber + " есть в телефонной книге.");
                return;
            }
        }
        System.out.println("Телефонный номер " + phoneNumber + " отсутствует в телефонной книге.");
    }
}
