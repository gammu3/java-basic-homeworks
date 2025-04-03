package ru.otus.java.basic.homeworks.hw07;

class AllTerrainVehicle extends Transport {
    private double currentFuel;

    public AllTerrainVehicle(String name) {
        super(name);
        this.currentFuel = 100;
    }

    @Override
    protected boolean isMoveOnTerrain(TerrainType terrain) {
        return true;
    }

    @Override
    protected boolean movement(double distance, TerrainType terrain, String humanName) {
        double fuelNeeded = distance * 0.15; // Вездеход расходует больше топлива
        if (currentFuel >= fuelNeeded) {
            currentFuel -= fuelNeeded;
            System.out.println(humanName + " сидит в " + name + " и использовал " + fuelNeeded + ".л топлива для премещения на " + distance + " км. Осталось:" + currentFuel + ".л");

            return true;
        } else {
            System.out.println(name + " не хватает топлива для перемещения на " + distance + " км");
            return false;
        }
    }
}
