package com.skyblock.database;

import com.skyblock.SkyBlockPlugin;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    private final SkyBlockPlugin plugin;
    private HikariDataSource dataSource;

    public DatabaseManager(SkyBlockPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        HikariConfig config = new HikariConfig();
        // Fallback to SQLite if MySQL isn't configured, ensuring it works out of the box
        config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/database.db");
        config.setPoolName("SkyBlockPool");
        config.setMaximumPoolSize(10);
        
        dataSource = new HikariDataSource(config);
        createTables();
    }

    private void createTables() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Connection conn = dataSource.getConnection()) {
                String createSkills = "CREATE TABLE IF NOT EXISTS player_skills (" +
                        "uuid VARCHAR(36) PRIMARY KEY, " +
                        "combat DOUBLE DEFAULT 0, mining DOUBLE DEFAULT 0, " +
                        "farming DOUBLE DEFAULT 0, fishing DOUBLE DEFAULT 0, " +
                        "foraging DOUBLE DEFAULT 0, enchanting DOUBLE DEFAULT 0, " +
                        "alchemy DOUBLE DEFAULT 0, taming DOUBLE DEFAULT 0)";
                
                String createStats = "CREATE TABLE IF NOT EXISTS player_stats (" +
                        "uuid VARCHAR(36) PRIMARY KEY, " +
                        "health DOUBLE DEFAULT 100, defense DOUBLE DEFAULT 0, " +
                        "strength DOUBLE DEFAULT 0, speed DOUBLE DEFAULT 100, " +
                        "crit_chance DOUBLE DEFAULT 30, crit_damage DOUBLE DEFAULT 50, " +
                        "intelligence DOUBLE DEFAULT 100)";
                
                try (PreparedStatement ps = conn.prepareStatement(createSkills)) {
                    ps.execute();
                }
                try (PreparedStatement ps2 = conn.prepareStatement(createStats)) {
                    ps2.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
