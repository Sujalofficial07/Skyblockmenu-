package com.skyblock;

import com.skyblock.database.DatabaseManager;
import com.skyblock.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlockPlugin extends JavaPlugin {

    private static SkyBlockPlugin instance;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;

        databaseManager = new DatabaseManager();
        databaseManager.init();

        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);

        getCommand("sbmenu").setExecutor((sender, command, label, args) -> {
            if (sender instanceof org.bukkit.entity.Player player) {
                com.skyblock.menu.MenuManager.openMainMenu(player);
            }
            return true;
        });
    }

    public static SkyBlockPlugin getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
