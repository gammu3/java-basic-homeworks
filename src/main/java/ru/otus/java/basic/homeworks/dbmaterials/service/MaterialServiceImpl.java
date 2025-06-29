package ru.otus.java.basic.homeworks.dbmaterials.service;

import ru.otus.java.basic.homeworks.dbmaterials.entity.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaterialServiceImpl implements MaterialService {
    private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);
    private final Connection connection;

    public MaterialServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Material> getAllMaterials() {
        logger.info("Получение списка всех материалов");
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
            logger.info("Успешно получено {} материалов", materials.size());
        } catch (SQLException e) {
            logger.error("Ошибка при получении списка материалов", e);
        }
        return materials;
    }

    @Override
    public Material getMaterialById(int id) {
        if (id <= 0) {
            logger.warn("Попытка получить материал с недопустимым ID: {}", id);
            return null;
        }

        logger.info("Получение материала с ID: {}", id);
        String sql = "SELECT id, name, density, young_modulus, poisson_ratio FROM materials WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("density"),
                        rs.getDouble("young_modulus"),
                        rs.getDouble("poisson_ratio")
                );
                logger.info("Материал с ID {} успешно получен", id);
                return material;
            }
            logger.warn("Материал с ID {} не найден", id);
        } catch (SQLException e) {
            logger.error("Ошибка при получении материала с ID: " + id, e);
        }
        return null;
    }

    @Override
    public boolean addMaterial(Material material) {
        if (material == null || material.getName() == null || material.getName().trim().isEmpty()) {
            logger.warn("Попытка добавить невалидный материал");
            return false;
        }

        logger.info("Добавление нового материала: {}", material.getName());
        String sql = "INSERT INTO materials (name, density, young_modulus, poisson_ratio) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

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
                            connection.commit();
                            logger.info("Материал успешно добавлен с ID: {}", material.getId());
                            return true;
                        }
                    }
                }
                connection.rollback();
                logger.warn("Не удалось добавить материал: {}", material.getName());
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Ошибка при откате транзакции", ex);
            }
            logger.error("Ошибка при добавлении материала", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Ошибка при восстановлении autoCommit", e);
            }
        }
        return false;
    }

    @Override
    public boolean deleteMaterial(int id) {
        if (id <= 0) {
            logger.warn("Попытка удалить материал с недопустимым ID: {}", id);
            return false;
        }

        logger.info("Удаление материала с ID: {}", id);
        String sql = "DELETE FROM materials WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    connection.commit();
                    logger.info("Материал с ID {} успешно удален", id);
                    return true;
                }
                connection.rollback();
                logger.warn("Материал с ID {} не найден", id);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Ошибка при откате транзакции", ex);
            }
            logger.error("Ошибка при удалении материала с ID: " + id, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Ошибка при восстановлении autoCommit", e);
            }
        }
        return false;
    }

    @Override
    public boolean updateMaterial(Material material) {
        if (material == null || material.getId() <= 0 || material.getName() == null || material.getName().trim().isEmpty()) {
            logger.warn("Попытка обновить невалидный материал");
            return false;
        }

        logger.info("Обновление материала с ID: {}", material.getId());
        String sql = "UPDATE materials SET name = ?, density = ?, young_modulus = ?, poisson_ratio = ? WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, material.getName());
                stmt.setDouble(2, material.getDensity());
                stmt.setDouble(3, material.getYoungModulus());
                stmt.setDouble(4, material.getPoissonRatio());
                stmt.setInt(5, material.getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    connection.commit();
                    logger.info("Материал с ID {} успешно обновлен", material.getId());
                    return true;
                }
                connection.rollback();
                logger.warn("Материал с ID {} не найден", material.getId());
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Ошибка при откате транзакции", ex);
            }
            logger.error("Ошибка при обновлении материала с ID: " + material.getId(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Ошибка при восстановлении autoCommit", e);
            }
        }
        return false;
    }
}