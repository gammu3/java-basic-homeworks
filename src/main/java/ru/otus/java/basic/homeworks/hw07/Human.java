package ru.otus.java.basic.homeworks.hw07;

public class Human {
    private String name;
    private Transport currentTransport;
    private int currentStamina;

    public Human(String human) {
        this.name = human;
        this.currentStamina = 100;
    }

    public void mount(Transport transport) {
        if (currentTransport != null) {
            System.out.println(name + " уже использует " + currentTransport);
            return;
        }
        currentTransport = transport;
        System.out.println(name + " сел(а) на " + transport);
    }

    public void dismount() {
        if (currentTransport == null) {
            System.out.println(name + " не использует транспорт");
            return;
        }
        System.out.println(name + " сошел(а) с " + currentTransport);
        currentTransport = null;
    }

    public boolean move(double distance, TerrainType terrain) {
        if (currentTransport != null) {
            return currentTransport.move(distance, terrain, name);
        } else {
            int staminaNeeded = (int) (distance * 5);
            if (currentStamina >= staminaNeeded) {
                currentStamina -= staminaNeeded;
                System.out.println(name + " прошел(а) " + distance + " км пешком по " + terrain.getTerrain() + ". Потратил(а) " + staminaNeeded + " ед. Осталось:" + currentStamina + ".ед");
                return true;
            } else {
                System.out.println(name + " слишком устала для перемещения на " + distance + " км");
                return false;
            }
        }
    }

    @Override
    public String toString() {
        if (currentTransport != null) {
            return name + (" на " + currentTransport);
        } else {
            return name + " не имеет транспортного средства.";
        }
    }
}
