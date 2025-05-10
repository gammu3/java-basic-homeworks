package ru.otus.java.basic.homeworks.hw11;

public class MainApp {
    public static void main(String[] args) {

        PersonDataBase db = new PersonDataBase();

        Person manager = new Person("Max Verstappen", Position.MANAGER, 1L);
        Person director = new Person("Lando Norris", Position.DIRECTOR, 2L);
        Person developer = new Person("Kimi Antonelli", Position.DEVELOPER, 3L);
        Person janitor = new Person("Oscar Piastri", Position.JANITOR, 4L);
        Person branchDirector = new Person("George Russel", Position.BRANCH_DIRECTOR, 5L);
        Person qa = new Person("Alex Albon", Position.QA, 6L);

        db.add(manager);
        db.add(director);
        db.add(developer);
        db.add(janitor);
        db.add(branchDirector);
        db.add(qa);
    }
}
