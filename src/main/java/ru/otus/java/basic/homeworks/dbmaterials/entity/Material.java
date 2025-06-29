package ru.otus.java.basic.homeworks.dbmaterials.entity;

import java.util.Objects;

public class Material {
    private int id;
    private String name;
    private double density;
    private double youngModulus;
    private double poissonRatio;

    public Material() {
    }

    public Material(int id, String name, double density, double youngModulus, double poissonRatio) {
        this.id = id;
        this.name = name;
        this.density = density;
        this.youngModulus = youngModulus;
        this.poissonRatio = poissonRatio;
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

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getYoungModulus() {
        return youngModulus;
    }

    public void setYoungModulus(double youngModulus) {
        this.youngModulus = youngModulus;
    }

    public double getPoissonRatio() {
        return poissonRatio;
    }

    public void setPoissonRatio(double poissonRatio) {
        this.poissonRatio = poissonRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return id == material.id &&
                Double.compare(material.density, density) == 0 &&
                Double.compare(material.youngModulus, youngModulus) == 0 &&
                Double.compare(material.poissonRatio, poissonRatio) == 0 &&
                Objects.equals(name, material.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, density, youngModulus, poissonRatio);
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", density=" + density +
                ", youngModulus=" + youngModulus +
                ", poissonRatio=" + poissonRatio +
                '}';
    }
}