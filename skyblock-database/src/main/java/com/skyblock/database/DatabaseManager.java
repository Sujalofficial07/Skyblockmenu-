package com.skyblock.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {

    private HikariDataSource dataSource;
    private final Plugin plugin;

    public DatabaseManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(FileConfiguration config) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" +
                config.getString("database.host", "localhost") + ":" +
                config.getInt("database.port", 3306) + "/" +
                config.getString("database.name", "skyblock") +
                "?useSSL=false&autoReconnect=true&allowPublicKeyRetrieval=true");
        hikariConfig.setUsername(config.getString("database.username", "root"));
        hikariConfig.setPassword(config.getString("database.password", ""));
        hikariConfig.setMaximumPoolSize(config.getInt("database.pool-size", 10));
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setPoolName("SkyBlock-Pool");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        this.dataSource = new HikariDataSource(hikariConfig);
        createTables();
    }

    private void createTables() throws SQLException {
        try (Connection conn = getConnection()) {
            conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS player_skills (" +
                "uuid VARCHAR(36) NOT NULL," +
                "skill_type VARCHAR(32) NOT NULL," +
                "xp DOUBLE NOT NULL DEFAULT 0," +
                "level INT NOT NULL DEFAULT 0," +
                "PRIMARY KEY (uuid, skill_type)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
            ).executeUpdate();

            conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS player_collections (" +
                "uuid VARCHAR(36) NOT NULL," +
                "collection_type VARCHAR(64) NOT NULL," +
                "amount BIGINT NOT NULL DEFAULT 0," +
                "tier INT NOT NULL DEFAULT 0," +
                "PRIMARY KEY (uuid, collection_type)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
            ).executeUpdate();

            conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS player_profiles (" +
                "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                "username VARCHAR(16) NOT NULL," +
                "coins DOUBLE NOT NULL DEFAULT 0," +
                "bank_balance DOUBLE NOT NULL DEFAULT 0," +
                "fairy_souls INT NOT NULL DEFAULT 0," +
                "last_seen BIGINT NOT NULL DEFAULT 0" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
            ).executeUpdate();
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new SQLException("DataSource is not initialized or has been closed.");
        }
        return dataSource.getConnection();
    }

    public void closeQuietly(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try { c.close(); } catch (Exception ex) {
                    plugin.getLogger().log(Level.WARNING, "Failed to close resource", ex);
                }
            }
        }
    }

    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public boolean isConnected() {
        return dataSource != null && !dataSource.isClosed();
    }

    public void executeAsync(Runnable task) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, task);
    }

    public void executeSync(Runnable task) {
        plugin.getServer().getScheduler().runTask(plugin, task);
    }
}
