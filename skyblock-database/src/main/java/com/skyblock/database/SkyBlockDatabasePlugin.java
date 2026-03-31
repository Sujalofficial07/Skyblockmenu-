package com.skyblock.database;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class SkyBlockDatabasePlugin extends JavaPlugin {

    private static SkyBlockDatabasePlugin instance;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);
        try {
            databaseManager.initialize(getConfig());
            getLogger().info("Database connection established successfully.");
        } catch (SQLException e) {
            getLogger().severe("Failed to connect to database!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getServicesManager().register(
            DatabaseManager.class,
            databaseManager,
            this,
            org.bukkit.plugin.ServicePriority.Normal
        );

        getLogger().info("SkyBlockDatabase module loaded.");
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.shutdown();
        }
        getLogger().info("SkyBlockDatabase module unloaded.");
    }

    public static SkyBlockDatabasePlugin getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
