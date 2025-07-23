package ru.otus.java.basic.homeworks.hw20;

import java.util.ArrayList;

class Box<T extends Fruit> {
    private ArrayList<T> fruits = new ArrayList<>();

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public void addFruits(ArrayList<T> newFruits) {
        fruits.addAll(newFruits);
    }

    public float getWeight() {
        float totalWeight = 0.0f;
        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }
        return totalWeight;
    }

    public boolean compare(Box<?> anotherBox) {
        return Math.abs(this.getWeight() - anotherBox.getWeight()) < 0.0001;
    }

    public void transferFruitsTo(Box<T> anotherBox) {
        if (this == anotherBox) {
            return;
        }
        anotherBox.addFruits(this.fruits);
        this.fruits.clear();
    }

    public int getCount() {
        return fruits.size();
    }
}
