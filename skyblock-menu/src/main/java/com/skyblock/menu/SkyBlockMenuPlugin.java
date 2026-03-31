package com.skyblock.menu;

import com.skyblock.database.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlockMenuPlugin extends JavaPlugin {

    private static SkyBlockMenuPlugin instance;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        DatabaseManager databaseManager = getServer().getServicesManager().load(DatabaseManager.class);
        if (databaseManager == null) {
            getLogger().severe("DatabaseManager service not found! Is SkyBlockDatabase enabled?");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        menuManager = new MenuManager(this);

        getServer().getServicesManager().register(
            MenuManager.class,
            menuManager,
            this,
            org.bukkit.plugin.ServicePriority.Normal
        );

        getServer().getPluginManager().registerEvents(new MenuListener(menuManager, this), this);

        getCommand("sbmenu").setExecutor((sender, command, label, args) -> {
            if (!(sender instanceof org.bukkit.entity.Player player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }
            menuManager.openMenu(player, new MainSkyBlockMenu(menuManager));
            return true;
        });

        getLogger().info("SkyBlockMenu module loaded.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkyBlockMenu module unloaded.");
    }

    public static SkyBlockMenuPlugin getInstance() {
        return instance;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
