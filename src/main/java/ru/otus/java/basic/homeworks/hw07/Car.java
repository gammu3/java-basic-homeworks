package ru.otus.java.basic.homeworks.hw07;

class Car extends Transport {
    private double currentFuel;

    public Car(String name) {
        super(name);
        this.currentFuel = 50;
    }

    @Override
    protected boolean isMoveOnTerrain(TerrainType terrain) {
        return terrain != TerrainType.DENSE_FOREST && terrain != TerrainType.SWAMP;
    }

    @Override
    protected boolean movement(double distance, TerrainType terrain, String humanName) {
        double fuelNeeded = distance * 0.08; // 8 л топлива на 100 км
        if (currentFuel >= fuelNeeded) {
            currentFuel -= fuelNeeded;
            System.out.println(humanName + "сидит в " + name + " и использовал " + fuelNeeded + ".л топлива для премещения на " + distance + " км. Осталось:" + currentFuel + ".л");
            return true;
        } else {
            System.out.println(name + " не хватает топлива для перемещения на " + distance + " км");
            return false;
        }
    }

}
