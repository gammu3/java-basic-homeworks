package ru.otus.java.basic.homeworks.hw07;

enum TerrainType {
    DENSE_FOREST("густой лес"),
    PLAIN("равнина"),
    SWAMP("болото");

    private final String terrain;

    TerrainType(String terrain) {
        this.terrain = terrain;
    }

    public String getTerrain() {
        return terrain;
    }
}
