package ru.otus.java.basic.homeworks.hw07;

class Bicycle extends Transport {
    public Bicycle(String name) {
        super(name);
    }

    @Override
    protected boolean isMoveOnTerrain(TerrainType terrain) {
        return terrain != TerrainType.SWAMP;
    }

    @Override
    protected boolean movement(double distance, TerrainType terrain, String humanName) {
        System.out.println(humanName + " находясь на/в " + name + " успешно проехал на " + distance + " км по " + terrain.getTerrain());
        return true;

    }
}



