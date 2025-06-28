package ru.otus.java.basic.homeworks.dbmaterials.service;

import ru.otus.java.basic.homeworks.dbmaterials.entity.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialServiceImpl implements MaterialService {
    private final Connection connection;

    public MaterialServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT id, name, density, young_modulus, poisson_ratio FROM materials";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("density"),
                        rs.getDouble("young_modulus"),
                        rs.getDouble("poisson_ratio")
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    @Override
    public Material getMaterialById(int id) {
        String sql = "SELECT id, name, density, young_modulus, poisson_ratio FROM materials WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("density"),
                        rs.getDouble("young_modulus"),
                        rs.getDouble("poisson_ratio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addMaterial(Material material) {
        String sql = "INSERT INTO materials (name, density, young_modulus, poisson_ratio) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, material.getName());
            stmt.setDouble(2, material.getDensity());
            stmt.setDouble(3, material.getYoungModulus());
            stmt.setDouble(4, material.getPoissonRatio());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        material.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMaterial(int id) {
        String sql = "DELETE FROM materials WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMaterial(Material material) {
        String sql = "UPDATE materials SET name = ?, density = ?, young_modulus = ?, poisson_ratio = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, material.getName());
            stmt.setDouble(2, material.getDensity());
            stmt.setDouble(3, material.getYoungModulus());
            stmt.setDouble(4, material.getPoissonRatio());
            stmt.setInt(5, material.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}