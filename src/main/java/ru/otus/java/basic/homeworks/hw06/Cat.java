package ru.otus.java.basic.homeworks.hw06;

public class Cat {
    private String name;
    private Integer appetite;
    private Boolean satiety;

    public Cat(String name, Integer appetite) {
        this.name = name;
        this.appetite = appetite;
        this.satiety = false;
    }

    public void eat(Plate plate) {
        if (plate.consumption(appetite)) {
            System.out.println(name + " поел из миски.");
            satiety = true;
        } else {
            System.out.println("Коту " + name + " не хватило еды в миске.");
            satiety = false;
        }
    }

    public void infoSatiety() {
        if (satiety) {
            System.out.println("Кот " + name + " сыт.");
        } else {
            System.out.println("Кот " + name + " голоден.");
        }
    }


}
