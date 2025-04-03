package ru.otus.java.basic.homeworks.hw07;

class Horse extends Transport {
    private int currentStamina;

    public Horse(String name) {
        super(name);
        this.currentStamina = 100;
    }

    @Override
    protected boolean isMoveOnTerrain(TerrainType terrain) {
        return terrain != TerrainType.SWAMP;
    }

    @Override
    protected boolean movement(double distance, TerrainType terrain, String humanName) {
        int staminaNeeded = (int) (distance * 2); // 2 единицы сил на 1 км

        if (this.currentStamina >= staminaNeeded) {
            this.currentStamina -= staminaNeeded;
            System.out.println(humanName + " сидит на " + name + ". И " + name + " использовал/а " + staminaNeeded + ".ед сил для перемещения на " + distance + " км. Осталось:" + this.currentStamina + ".ед");
            return true;
        } else {
            System.out.println(name + " слишком устала для перемещения на " + distance + " км");
            return false;
        }
    }
}
