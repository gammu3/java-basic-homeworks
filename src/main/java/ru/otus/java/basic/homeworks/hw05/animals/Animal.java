package ru.otus.java.basic.homeworks.hw05.animals;

public abstract class Animal {
    String name;
    Integer runSpeed;
    Integer swimSpeed;
    Integer stamina;
    Integer MaxStamina;


    public Animal(String name, Integer runSpeed, Integer swimSpeed, Integer stamina) {
        this.name = name;
        this.runSpeed = runSpeed;
        this.swimSpeed = swimSpeed;
        this.stamina = stamina;
        this.MaxStamina = stamina;
    }

    public void info() {
        System.out.println(name + ": Скорость бега: " + runSpeed + " Скорость плавания: " + swimSpeed + " Выносливость: " + stamina + "/" + MaxStamina);
    }

    public float run(int distance) {
        if (distance * getRunStaminaCost() > stamina) {
            System.out.println(name + " устал и пока не может бегать на запрошенное расстояние.");
            return -1;
        }
        float time = (float) distance / runSpeed;
        stamina -= (distance * getRunStaminaCost());
        System.out.println(name + " Пробежал " + distance + " метров за " + String.format("%.2f", time) + " секунд. Затратил " + distance * getRunStaminaCost() + " ед. выносливости.");
        return time;
    }

    public float swim(int distance) {
        if (distance * getSwimStaminaCost() > stamina) {
            System.out.println(name + " устал и пока не может плавать на запрошенное расстояние.");
            return -1;
        }
        float time = (float) distance / runSpeed;
        stamina -= (distance * getSwimStaminaCost());
        System.out.println(name + " Проплыл " + distance + " метров за " + String.format("%.2f", time) + " секунд. Затратил " + distance * getSwimStaminaCost() + " ед. выносливости.");
        return time;
    }

    public abstract int getRunStaminaCost();

    public abstract int getSwimStaminaCost();


}
