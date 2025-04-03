package ru.otus.java.basic.homeworks.hw07;

public class AppMain {
    public static void main(String[] args) {
        Human human1 = new Human("Пётр");

        Car car = new Car("Renault Duster");
        Horse horse = new Horse("Пятнышко");
        Bicycle bicycle = new Bicycle("Jamis");
        AllTerrainVehicle allterrainvehicle = new AllTerrainVehicle("Судно на воздушной подушке");

        human1.move(1000, TerrainType.PLAIN);
        human1.move(10, TerrainType.PLAIN);
        System.out.println(human1);
        human1.mount(car);
        human1.move(2000, TerrainType.PLAIN);
        human1.move(5, TerrainType.SWAMP);
        System.out.println(human1);
        human1.dismount();
        human1.move(10, TerrainType.PLAIN);
        human1.mount(horse);
        human1.move(15, TerrainType.DENSE_FOREST);
        human1.move(10, TerrainType.SWAMP);
        System.out.println(human1);
        human1.dismount();
        human1.mount(bicycle);
        human1.move(8, TerrainType.PLAIN);

        human1.dismount();
        human1.mount(allterrainvehicle);
        human1.move(12, TerrainType.SWAMP);


    }
}
