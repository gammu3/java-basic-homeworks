package ru.otus.java.basic.homeworks.dbmaterials.service;

import ru.otus.java.basic.homeworks.dbmaterials.entity.Material;
import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterials();
    Material getMaterialById(int id);
    boolean addMaterial(Material material);
    boolean deleteMaterial(int id);
    boolean updateMaterial(Material material);
}