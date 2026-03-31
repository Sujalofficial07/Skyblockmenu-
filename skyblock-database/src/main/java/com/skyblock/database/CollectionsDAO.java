package com.skyblock.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class CollectionsDAO {

    private final DatabaseManager db;

    public CollectionsDAO(DatabaseManager db) {
        this.db = db;
    }

    public void loadCollections(UUID uuid, Consumer<Map<String, long[]>> callback) {
        db.executeAsync(() -> {
            Map<String, long[]> result = new HashMap<>();
            String sql = "SELECT collection_type, amount, tier FROM player_collections WHERE uuid = ?";
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = db.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, uuid.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    String type = rs.getString("collection_type");
                    long amount = rs.getLong("amount");
                    int tier = rs.getInt("tier");
                    result.put(type, new long[]{amount, tier});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeQuietly(rs, ps, conn);
            }
            db.executeSync(() -> callback.accept(result));
        });
    }

    public void addCollection(UUID uuid, String collectionType, long amount, Consumer<long[]> callback) {
        db.executeAsync(() -> {
            String selectSql = "SELECT amount, tier FROM player_collections WHERE uuid = ? AND collection_type = ?";
            String upsertSql = "INSERT INTO player_collections (uuid, collection_type, amount, tier) VALUES (?, ?, ?, ?) " +
                               "ON DUPLICATE KEY UPDATE amount = VALUES(amount), tier = VALUES(tier)";
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = db.getConnection();
                ps = conn.prepareStatement(selectSql);
                ps.setString(1, uuid.toString());
                ps.setString(2, collectionType);
                rs = ps.executeQuery();
                long currentAmount = 0;
                if (rs.next()) {
                    currentAmount = rs.getLong("amount");
                }
                db.closeQuietly(rs, ps);
                long newAmount = currentAmount + amount;
                int newTier = CollectionTierCalculator.calculateTier(collectionType, newAmount);
                ps = conn.prepareStatement(upsertSql);
                ps.setString(1, uuid.toString());
                ps.setString(2, collectionType);
                ps.setLong(3, newAmount);
                ps.setInt(4, newTier);
                ps.executeUpdate();
                long finalAmount = newAmount;
                int finalTier = newTier;
                db.executeSync(() -> callback.accept(new long[]{finalAmount, finalTier}));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeQuietly(rs, ps, conn);
            }
        });
    }

    public void saveCollection(UUID uuid, String collectionType, long amount, int tier) {
        db.executeAsync(() -> {
            String sql = "INSERT INTO player_collections (uuid, collection_type, amount, tier) VALUES (?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE amount = VALUES(amount), tier = VALUES(tier)";
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = db.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, uuid.toString());
                ps.setString(2, collectionType);
                ps.setLong(3, amount);
                ps.setInt(4, tier);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeQuietly(ps, conn);
            }
        });
    }
}
