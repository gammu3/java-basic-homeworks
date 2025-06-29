package ru.otus.java.basic.homeworks.dbmaterials.entity;

import java.util.Objects;

public class MaterialProperty {
    private int id;
    private String name;
    private String value;

    public MaterialProperty(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MaterialProperty that = (MaterialProperty) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }

    @Override
    public String toString() {
        return "MaterialProperty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}