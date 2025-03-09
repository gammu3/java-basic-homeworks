package ru.otus.java.basic.homeworks.hw05.animals;

public class Cat extends Animal {
    private final int STAMINA_COST_RUN = 1;
    private final int STAMINA_COST_SWIM = 0;

    public Cat(String name, Integer runSpeed, Integer swimSpeed, Integer stamina) {
        super(name, runSpeed, 0, stamina);
    }

    public int getRunStaminaCost() {
        return STAMINA_COST_RUN;
    }

    public int getSwimStaminaCost() {
        return STAMINA_COST_SWIM;
    }

    @Override
    public float swim(int distance) {
        System.out.println(name + " Ничего не проплыл. Кошки не умеют плавать!");
        return -1;
    }
}
