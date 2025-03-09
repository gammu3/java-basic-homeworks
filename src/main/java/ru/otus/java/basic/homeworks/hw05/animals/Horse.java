package ru.otus.java.basic.homeworks.hw05.animals;

public class Horse extends Animal {
    private final int STAMINA_COST_RUN = 1;
    private final int STAMINA_COST_SWIM = 4;

    public Horse(String name, Integer runSpeed, Integer swimSpeed, Integer stamina) {
        super(name, runSpeed, swimSpeed, stamina);
    }

    public int getRunStaminaCost() {
        return STAMINA_COST_RUN;
    }

    public int getSwimStaminaCost() {
        return STAMINA_COST_SWIM;
    }

}
