package ru.otus.java.basic.homeworks.hw06;

public class Plate {
    private Integer maxFoodCount;
    private Integer currentFoodCount;

    public Plate(Integer maxFoodCount) {
        this.currentFoodCount = maxFoodCount;
        this.maxFoodCount = maxFoodCount;
    }

    public void addFood(Integer countFoodToAdd) {
        if(currentFoodCount != maxFoodCount) {
            if (currentFoodCount + countFoodToAdd > maxFoodCount) {
                countFoodToAdd = maxFoodCount - currentFoodCount;
                currentFoodCount = maxFoodCount;
            } else {
                currentFoodCount += countFoodToAdd;
            }
            System.out.print("В миску досыпали " + countFoodToAdd + " еды. ");
            this.info();
        } else {
            System.out.println("Миска уже полная. Некуда добавлять еду.");
        }
    }

    public boolean consumption(Integer request) {
        if (currentFoodCount >= request) {
            currentFoodCount -= request;
            return true;
        }
        return false;
    }

    public void info() {
        System.out.println("Сейчас еды в миске: " + currentFoodCount + "/" + maxFoodCount);
    }

}
