package com.skyblock.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class SkillsDAO {

    private final DatabaseManager db;

    public SkillsDAO(DatabaseManager db) {
        this.db = db;
    }

    public void loadSkills(UUID uuid, Consumer<Map<String, double[]>> callback) {
        db.executeAsync(() -> {
            Map<String, double[]> result = new HashMap<>();
            String sql = "SELECT skill_type, xp, level FROM player_skills WHERE uuid = ?";
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = db.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, uuid.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    String type = rs.getString("skill_type");
                    double xp = rs.getDouble("xp");
                    int level = rs.getInt("level");
                    result.put(type, new double[]{xp, level});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeQuietly(rs, ps, conn);
            }
            db.executeSync(() -> callback.accept(result));
        });
    }

    public void saveSkill(UUID uuid, String skillType, double xp, int level) {
        db.executeAsync(() -> {
            String sql = "INSERT INTO player_skills (uuid, skill_type, xp, level) VALUES (?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE xp = VALUES(xp), level = VALUES(level)";
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = db.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, uuid.toString());
                ps.setString(2, skillType);
                ps.setDouble(3, xp);
                ps.setInt(4, level);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeQuietly(ps, conn);
            }
        });
    }

    public void addXp(UUID uuid, String skillType, double xpToAdd, Consumer<double[]> callback) {
        db.executeAsync(() -> {
            String selectSql = "SELECT xp, level FROM player_skills WHERE uuid = ? AND skill_type = ?";
            String upsertSql = "INSERT INTO player_skills (uuid, skill_type, xp, level) VALUES (?, ?, ?, ?) " +
                               "ON DUPLICATE KEY UPDATE xp = VALUES(xp), level = VALUES(level)";
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = db.getConnection();
                ps = conn.prepareStatement(selectSql);
                ps.setString(1, uuid.toString());
                ps.setString(2, skillType);
                rs = ps.executeQuery();
                double currentXp = 0;
                int currentLevel = 0;
                if (rs.next()) {
                    currentXp = rs.getDouble("xp");
                    currentLevel = rs.getInt("level");
                }
                db.closeQuietly(rs, ps);
                double newXp = currentXp + xpToAdd;
                int newLevel = SkillLevelCalculator.calculateLevel(skillType, newXp);
                ps = conn.prepareStatement(upsertSql);
                ps.setString(1, uuid.toString());
                ps.setString(2, skillType);
                ps.setDouble(3, newXp);
                ps.setInt(4, newLevel);
                ps.executeUpdate();
                double finalXp = newXp;
                int finalLevel = newLevel;
                db.executeSync(() -> callback.accept(new double[]{finalXp, finalLevel}));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeQuietly(rs, ps, conn);
            }
        });
    }
}
