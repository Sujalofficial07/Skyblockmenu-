package com.skyblock;

import com.skyblock.database.DatabaseManager;
import com.skyblock.listeners.BlockTrackerListener;
import com.skyblock.listeners.MenuListener;
import com.skyblock.menu.MenuCommand;
import com.skyblock.stats.StatsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlockPlugin extends JavaPlugin {

    private static SkyBlockPlugin instance;
    private DatabaseManager databaseManager;
    private StatsManager statsManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.init();

        this.statsManager = new StatsManager(this);

        getServer().getPluginManager().registerEvents(new BlockTrackerListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        
        getCommand("sbmenu").setExecutor(new MenuCommand());
        
        getLogger().info("SkyBlockCore has been enabled successfully!");
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.close();
        }
        getLogger().info("SkyBlockCore has been disabled.");
    }

    public static SkyBlockPlugin getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
