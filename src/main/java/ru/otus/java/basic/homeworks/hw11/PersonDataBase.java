package ru.otus.java.basic.homeworks.hw11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PersonDataBase {

    private Map<Long, Person> personsDb = new HashMap<>();
    private Set<Long> managersDb = new HashSet<>();


    public void add(Person person) {
        if (person == null || person.getId() == null) {
            throw new IllegalArgumentException("Person and ID must not be null");
        }
        personsDb.put(person.getId(), person);

        if (isManager(person)) {
            managersDb.add(person.getId());
        }
    }

    public Person findById(Long id) {
        return personsDb.get(id);
    }

    public boolean isManager(Person person) {
        if (person == null) return false;
        Position position = person.getPosition();
        return position == Position.MANAGER || position == Position.DIRECTOR || position == Position.BRANCH_DIRECTOR || position == Position.SENIOR_MANAGER;
    }

    public boolean isEmployee(Long id) {
        if (id == null) return false;
        return personsDb.containsKey(id) && !managersDb.contains(id);
    }
}
