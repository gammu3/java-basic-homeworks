package ru.otus.java.basic.homeworks.hw07;

public abstract class Transport {
    String name;

    public Transport(String name) {
        this.name = name;
    }

    public boolean move(double distance, TerrainType terrain, String humanName) {
        if (!isMoveOnTerrain(terrain)) {
            System.out.println(humanName + " находится на/в " + name + ", но " + name + " не может перемещаться по местности: " + terrain.getTerrain());
            return false;
        }
        return movement(distance, terrain, humanName);
    }

    protected abstract boolean isMoveOnTerrain(TerrainType terrain);

    protected abstract boolean movement(double distance, TerrainType terrain, String humanName);

    @Override
    public String toString() {
        return name;
    }
}
