package ru.otus.java.basic.homeworks.hw11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PersonDataBase {
    private static final Set<Position> MANAGER_POSITIONS = new HashSet<>() {{
        add(Position.MANAGER);
        add(Position.DIRECTOR);
        add(Position.BRANCH_DIRECTOR);
        add(Position.SENIOR_MANAGER);
    }};

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
        if (person == null) {
            return false;
        }
        return MANAGER_POSITIONS.contains(person.getPosition());
    }

    public boolean isEmployee(Long id) {
        if (id == null) return false;
        return personsDb.containsKey(id) && !managersDb.contains(id);
    }
}
