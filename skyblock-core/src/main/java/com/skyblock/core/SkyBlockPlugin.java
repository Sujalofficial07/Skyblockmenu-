package com.skyblock.core;

import com.skyblock.collections.CollectionListener;
import com.skyblock.collections.CollectionManager;
import com.skyblock.database.DatabaseManager;
import com.skyblock.menu.MenuItemBuilder;
import com.skyblock.menu.MenuListener;
import com.skyblock.menu.MenuManager;
import com.skyblock.skills.SkillManager;
import com.skyblock.skills.SkillXpListener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class SkyBlockPlugin extends JavaPlugin {

    private DatabaseManager databaseManager;
    private MenuManager menuManager;
    private SkillManager skillManager;
    private CollectionManager collectionManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);
        try {
            databaseManager.initialize(getConfig());
        } catch (SQLException e) {
            getLogger().severe("Failed to connect to database! Disabling plugin.");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        menuManager = new MenuManager(this);
        skillManager = new SkillManager(this, databaseManager);
        collectionManager = new CollectionManager(this, databaseManager);

        getServer().getServicesManager().register(MenuManager.class, menuManager, this, ServicePriority.Normal);
        getServer().getServicesManager().register(SkillManager.class, skillManager, this, ServicePriority.Normal);
        getServer().getServicesManager().register(CollectionManager.class, collectionManager, this, ServicePriority.Normal);
        getServer().getServicesManager().register(DatabaseManager.class, databaseManager, this, ServicePriority.Normal);

        getServer().getPluginManager().registerEvents(new MenuListener(menuManager, this), this);
        getServer().getPluginManager().registerEvents(new SkillXpListener(skillManager, this), this);
        getServer().getPluginManager().registerEvents(new CollectionListener(collectionManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, skillManager, collectionManager), this);

        getCommand("skyblock").setExecutor(new SkyBlockCommand(this, menuManager));
        getCommand("skills").setExecutor(new SkillsCommand(this, menuManager));
        getCommand("collections").setExecutor(new CollectionsCommand(this, menuManager));

        getLogger().info("SkyBlock plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.shutdown();
        }
        getLogger().info("SkyBlock plugin disabled.");
    }

    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public MenuManager getMenuManager() { return menuManager; }
    public SkillManager getSkillManager() { return skillManager; }
    public CollectionManager getCollectionManager() { return collectionManager; }
}
